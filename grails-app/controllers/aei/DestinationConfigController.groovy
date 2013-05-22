package aei

import org.springframework.dao.DataIntegrityViolationException

class DestinationConfigController {

    static allowedMethods = [save: "POST", update: "POST", delete: "POST"]

    def index() {
        redirect(action: "list", params: params)
    }

    def list(Integer max) {
        params.max = Math.min(max ?: 10, 100)
        [destinationConfigInstanceList: DestinationConfig.list(params), destinationConfigInstanceTotal: DestinationConfig.count()]
    }

    def create() {
    }

    def save() {
        def destinationConfig
        if (params.port) 
          destinationConfig = new AppDestinationConfig(params)
        else
          destinationConfig = new EmailDestinationConfig(params)

        if (!destinationConfig.save(flush: true))
        {
            render(view: "create", model: [destinationConfigInstance: destinationConfig])
            return
        }

        flash.message = message(code: 'default.created.message',
                                args: [message(code: 'destinationConfig.label',
                                default: 'DestinationConfig'), destinationConfig.id])
        redirect(action: "list")
    }

    def edit(Long id) {
        def destinationConfig = DestinationConfig.get(id)

        if (!destinationConfig) {
            flash.message = message(code: 'default.not.found.message', args: [message(code: 'destinationConfig.label', default: 'DestinationConfig'), id])
            redirect(action: "list")
            return
        }
        [destinationConfigInstance: destinationConfig]
    }

    def update(Long id, Long version) {
        def destinationConfigInstance = DestinationConfig.get(id)
        if (!destinationConfigInstance) {
            flash.message = message(code: 'default.not.found.message', args: [message(code: 'destinationConfig.label', default: 'DestinationConfig'), id])
            redirect(action: "list")
            return
        }

        if (version != null) {
            if (destinationConfigInstance.version > version) {
                destinationConfigInstance.errors.rejectValue("version", "default.optimistic.locking.failure",
                          [message(code: 'destinationConfig.label', default: 'DestinationConfig')] as Object[],
                          "Another user has updated this DestinationConfig while you were editing")
                render(view: "edit", model: [destinationConfigInstance: destinationConfigInstance])
                return
            }
        }

        destinationConfigInstance.properties = params

        if (!destinationConfigInstance.save(flush: true)) {
            render(view: "edit", model: [destinationConfigInstance: destinationConfigInstance])
            return
        }

        flash.message = message(code: 'default.updated.message', args: [message(code: 'destinationConfig.label', default: 'DestinationConfig'), destinationConfigInstance.id])
        redirect(action: "list")
    }

    def delete(Long id) {
        def destinationConfigInstance = DestinationConfig.get(id)
        if (!destinationConfigInstance) {
            flash.message = message(code: 'default.not.found.message', args: [message(code: 'destinationConfig.label', default: 'DestinationConfig'), id])
            redirect(action: "list")
            return
        }

        try {
            destinationConfigInstance.delete(flush: true)
            flash.message = message(code: 'default.deleted.message', args: [message(code: 'destinationConfig.label', default: 'DestinationConfig'), id])
            redirect(action: "list")
        }
        catch (DataIntegrityViolationException e) {
            flash.message = message(code: 'default.not.deleted.message', args: [message(code: 'destinationConfig.label', default: 'DestinationConfig'), id])
            redirect(action: "show", id: id)
        }
    }
}
