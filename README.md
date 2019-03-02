CaboLabs DICOM Broker!
======================

Grails application to query, retrieve, visualize DICOM Imaginology Studies and Structured Reports from a set of remote PACS.

## Basic flow

1\. Login using sample users (check Bootstrap.groovy)

![login](images/1-login.jpg)

2\. Search for studies, by ID or patient name

This search will be executed on all the PACS you have connected to the DICOM Broker! A common use case is when trying to access studies from a network of clinics or hospitals.

![search](images/2-search.jpg)

3\. Get studies back

![search study level](images/3-search-study.jpg)

4\. Navigate inside the studies to display series

![search series level](images/4-search-series.jpg)

5\. Check the objects on a series

![search object level](images/5-search-objects.jpg)

6\. Display an image object from the PACS

![display image](images/6-search-display-image.jpg)

7\. Display a report object from the PACS

![display report](images/7-search-display-report.jpg)


Contact me if you want to use it, extend it or adapt it. pablo.pazos@cabolabs.com
