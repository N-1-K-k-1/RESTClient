## Table of Contents

- [Quick start guide: how to use client.](#quick-start-guide-how-to-use-client)  
  1.[Main window](#main-window)  
  2.[POST REQUEST](#post-request)  
  3.[GET REQUEST](#get-request)  
  4.[UPDATE REQUEST](#update-request)  
  5.[DELETE REQUEST](#delete-request)  
  6.[ALL 4 REQUESTS](#all-4-requests)  
  7.[Unset correlationId](#unset-correlationid)  
  8.[Test throughput](#test-throughput)  
  9.[Choose CSV FILE, Reset opened file](#choose-csv-file-reset-opened-file)  
- [Build and run application](#build-and-run-application)

## Quick start guide: how to use client

There are few buttons on the UI, five of them for query execution. Every response time result is saved to a file TestResponse.csv.
Also, there are buttons to choose CSV file, if you want to add the new response time result to the existing file, and button to test throughput. 
Parameter "CorrelationId" is responsible for variable that changes after each thread executes a request and this variable is a parameter that is passed to the POST, GET, PUT, DELETE functions as an ID parameter. It can be reset by pressing the Unset correlationId button.

There are some problem using it with different servers — it is about changing the IP address of the server in the code. Immutable variable *reqUrl* should be changed in files: *All4Requests.kt*, *DeleteCustomer.kt*, *GetCustomer.kt*, *PostCustomer.kt*, *Throughput.kt* and *UpdateCustomer.kt*. In the near future I plan to add the ability to change the IP address from the UI. 
So, there is example of using the client:  

 1. #### **Main window**  
 ![enter image description here](https://bitbucket.org/N1Kk1/kotlinrestclient/raw/698b1c2a0c1bef9338a4a120a42533c72479bcaf/imgs/main.png)
 
 2. #### **POST REQUEST**  
 After clicking POST REQUEST button you should enter the number of clients.  
 ![enter image description here](https://bitbucket.org/N1Kk1/kotlinrestclient/raw/698b1c2a0c1bef9338a4a120a42533c72479bcaf/imgs/POST1.png)  
 Then, push to the ENTER and you will see the response and the time of the response.  
 ![enter image description here](https://bitbucket.org/N1Kk1/kotlinrestclient/raw/698b1c2a0c1bef9338a4a120a42533c72479bcaf/imgs/POST2.png)

 3. #### **GET REQUEST**  
 ![enter image description here](https://bitbucket.org/N1Kk1/kotlinrestclient/raw/698b1c2a0c1bef9338a4a120a42533c72479bcaf/imgs/GET.png)

 4. #### **UPDATE REQUEST**  
 ![enter image description here](https://bitbucket.org/N1Kk1/kotlinrestclient/raw/698b1c2a0c1bef9338a4a120a42533c72479bcaf/imgs/UPDATE.png)  

 5. #### **DELETE REQUEST**  
 ![enter image description here](https://bitbucket.org/N1Kk1/kotlinrestclient/raw/698b1c2a0c1bef9338a4a120a42533c72479bcaf/imgs/DELETE.png)  

 6. #### **ALL 4 REQUESTS**  
 This button executes all previous requests together.  
 ![enter image description here](https://bitbucket.org/N1Kk1/kotlinrestclient/raw/698b1c2a0c1bef9338a4a120a42533c72479bcaf/imgs/ALLFOUR1.png)  
 Press ENTER and you will see response time of the four requests and response of the last (DELETE) request.  
 ![enter image description here](https://bitbucket.org/N1Kk1/kotlinrestclient/raw/698b1c2a0c1bef9338a4a120a42533c72479bcaf/imgs/ALLFOUR2.png)  

 7. #### **Unset correlationId**  
 If you want, you can reset the correlationId.  
 ![enter image description here](https://bitbucket.org/N1Kk1/kotlinrestclient/raw/698b1c2a0c1bef9338a4a120a42533c72479bcaf/imgs/UNSETCORRID.png)  

 8. #### **Test throughput**  
 After you press this button you need to enter the file name.  
 ![enter image description here](https://bitbucket.org/N1Kk1/kotlinrestclient/raw/698b1c2a0c1bef9338a4a120a42533c72479bcaf/imgs/TESTTHROUGHPUT1.png)  
 Then push enter and you will see the received image, its size, response time and throughput. Measurement results will be saved to TestThroughput.csv file.  
 ![enter image description here](https://bitbucket.org/N1Kk1/kotlinrestclient/raw/698b1c2a0c1bef9338a4a120a42533c72479bcaf/imgs/TESTTHROUGHPUT2.png)  
 
 9. #### **Choose CSV file, Reset opened file**  
 When you want to save the measurement results to the existing file, you need to open this file.  
 ![enter image description here](https://bitbucket.org/N1Kk1/kotlinrestclient/raw/698b1c2a0c1bef9338a4a120a42533c72479bcaf/imgs/CHOOSEFILE.png)  
 And if you want to save results to the new file press the Reset opened file button.  

## Build and run application

 Using maven, you can run the application by using: 
     
     ./mvnw clean install
 And then run the JAR file, as follows:
     
     java -jar target/kotlinRESTClient-0.0.1-SNAPSHOT.jar
                      

