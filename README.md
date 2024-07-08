Let's build the Quarkus application according to your structure and requirements. We'll create a full example including the necessary classes and explanations.

1. Folder Structure Overview
css
Copy code
src/
├── main/
│   ├── java/
│   │   └── com/
│   │       └── sc/
│   │           └── faas/
│   │               ├── dto/
│   │               │   └── MyObject.java
│   │               ├── model/
│   │               │   ├── CopsApplicationStatusSG.java
│   │               │   └── CopsCustIndicatorsSG.java
│   │               ├── repository/
│   │               │   ├── CopsApplicationStatusSGRepository.java
│   │               │   └── CopsCustIndicatorsSGRepository.java
│   │               ├── resource/
│   │               │   └── FileUploadResource.java
│   │               ├── service/
│   │               │   ├── FileProcessor.java
│   │               │   ├── FileReader.java
│   │               │   ├── FileUploadService.java
│   │               │   ├── FileWriter.java
│   │               │   └── ProcessService.java
│   │               └── util/
│   │                   ├── CSVParser.java
│   │                   └── Function.java
│   ├── resources/
│   │   ├── application.properties
│   │   └── COPS_APPLICATION_STATUS_SG.csv
└── test/
    └── java/
        └── com/
            └── sc/
                └── faas/
                    ├── FileUploadResourceTest.java
                    ├── FileProcessorTest.java
                    ├── FileReaderTest.java
                    ├── FileUploadServiceTest.java
                    ├── FileWriterTest.java
                    └── ProcessServiceTest.java
2. Entity Classes
Create the entity classes for CopsApplicationStatusSG and CopsCustIndicatorsSG.

CopsApplicationStatusSG.java

java
Copy code
package com.sc.faas.model;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.time.LocalDate;

@Entity
@Table(name = "COPS_APPLICATION_STATUS_SG")
public class CopsApplicationStatusSG {
    @Id
    private Long nApplnRefNo;
    private LocalDate dApplnCreat;
    private String xStatusDesc;
    private String xApplnStatus;
    private String xPrdCtgry;
    private LocalDate dCreat;
    private LocalDate dUpd;
    private String xCreat;
    private String xUpd;

    // Getters and Setters
}
CopsCustIndicatorsSG.java

java
Copy code
package com.sc.faas.model;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.time.LocalDate;

@Entity
@Table(name = "COPS_CUST_INDICATORS_SG")
public class CopsCustIndicatorsSG {
    @Id
    private String relId;
    private String fFeeWaiver;
    private String fKycStatus;
    private String fTransferExclusion;
    private String fSensitiveCust;
    private String rbsCust;
    private Long nFeeWaiverFileId;
    private Long nKycStatusFileId;
    private Long nTransferExclusionFileId;
    private Long nSensitiveCustFileId;
    private Long nRbsCustFileId;
    private LocalDate dCreat;
    private LocalDate dUpd;
    private String xCreat;
    private String xUpd;

    // Getters and Setters
}
3. Repository Interfaces
Create repository interfaces for the entities.

CopsApplicationStatusSGRepository.java

java
Copy code
package com.sc.faas.repository;

import com.sc.faas.model.CopsApplicationStatusSG;
import io.quarkus.hibernate.orm.panache.PanacheRepository;
import javax.enterprise.context.ApplicationScoped;

@ApplicationScoped
public class CopsApplicationStatusSGRepository implements PanacheRepository<CopsApplicationStatusSG> {
}
CopsCustIndicatorsSGRepository.java

java
Copy code
package com.sc.faas.repository;

import com.sc.faas.model.CopsCustIndicatorsSG;
import io.quarkus.hibernate.orm.panache.PanacheRepository;
import javax.enterprise.context.ApplicationScoped;

@ApplicationScoped
public class CopsCustIndicatorsSGRepository implements PanacheRepository<CopsCustIndicatorsSG> {
}
4. Service Classes
Create service classes to handle file reading, processing, and writing.

FileReader.java

java
Copy code
package com.sc.faas.service;

import com.sc.faas.util.CSVParser;
import javax.enterprise.context.ApplicationScoped;
import java.io.InputStream;
import java.util.List;

@ApplicationScoped
public class FileReader {
    public List<String[]> readCSV(InputStream inputStream) {
        return CSVParser.parse(inputStream);
    }
}
FileProcessor.java

java
Copy code
package com.sc.faas.service;

import com.sc.faas.model.CopsApplicationStatusSG;
import com.sc.faas.model.CopsCustIndicatorsSG;

import javax.enterprise.context.ApplicationScoped;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.stream.Collectors;

@ApplicationScoped
public class FileProcessor {
    private static final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");

    public List<CopsApplicationStatusSG> processApplicationStatus(List<String[]> csvData) {
        return csvData.stream().map(record -> {
            CopsApplicationStatusSG entity = new CopsApplicationStatusSG();
            entity.setNApplnRefNo(Long.parseLong(record[0]));
            entity.setDApplnCreat(LocalDate.parse(record[1], formatter));
            entity.setXStatusDesc(record[2]);
            entity.setXApplnStatus(record[3]);
            entity.setXPrdCtgry(record[4]);
            entity.setDCreat(LocalDate.parse(record[5], formatter));
            entity.setDUpd(LocalDate.parse(record[6], formatter));
            entity.setXCreat(record[7]);
            entity.setXUpd(record[8]);
            return entity;
        }).collect(Collectors.toList());
    }

    public List<CopsCustIndicatorsSG> processCustIndicators(List<String[]> csvData) {
        return csvData.stream().map(record -> {
            CopsCustIndicatorsSG entity = new CopsCustIndicatorsSG();
            entity.setRelId(record[0]);
            entity.setFFeeWaiver(record[1]);
            entity.setFKycStatus(record[2]);
            entity.setFTransferExclusion(record[3]);
            entity.setFSensitiveCust(record[4]);
            entity.setRbsCust(record[5]);
            entity.setNFeeWaiverFileId(Long.parseLong(record[6]));
            entity.setNKycStatusFileId(Long.parseLong(record[7]));
            entity.setNTransferExclusionFileId(Long.parseLong(record[8]));
            entity.setNSensitiveCustFileId(Long.parseLong(record[9]));
            entity.setNRbsCustFileId(Long.parseLong(record[10]));
            entity.setDCreat(LocalDate.parse(record[11], formatter));
            entity.setDUpd(LocalDate.parse(record[12], formatter));
            entity.setXCreat(record[13]);
            entity.setXUpd(record[14]);
            return entity;
        }).collect(Collectors.toList());
    }
}
FileWriter.java

java
Copy code
package com.sc.faas.service;

import com.sc.faas.model.CopsApplicationStatusSG;
import com.sc.faas.model.CopsCustIndicatorsSG;
import com.sc.faas.repository.CopsApplicationStatusSGRepository;
import com.sc.faas.repository.CopsCustIndicatorsSGRepository;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.transaction.Transactional;
import java.util.List;

@ApplicationScoped
public class FileWriter {
    @Inject
    CopsApplicationStatusSGRepository applicationStatusRepository;

    @Inject
    CopsCustIndicatorsSGRepository custIndicatorsRepository;

    @Transactional
    public void writeApplicationStatus(List<CopsApplicationStatusSG> data) {
        applicationStatusRepository.persist(data);
    }

    @Transactional
    public void writeCustIndicators(List<CopsCustIndicatorsSG> data) {
        custIndicatorsRepository.persist(data);
    }
}
FileUploadService.java

java
Copy code
package com.sc.faas.service;

import com.sc.faas.model.CopsApplicationStatusSG;
import com.sc.faas.model.CopsCustIndicatorsSG;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import java.io.InputStream;
import java.util.List;

@ApplicationScoped
public class FileUploadService {
    @Inject
    FileReader fileReader;

    @Inject
    FileProcessor fileProcessor;

    @Inject
    FileWriter fileWriter;

    public void uploadApplicationStatus(InputStream inputStream) {
        List<String[]> csvData = fileReader.readCSV(inputStream);
        List<CopsApplicationStatusSG> data = fileProcessor.processApplicationStatus(csvData);
        fileWriter.writeApplicationStatus(data);
    }

    public void uploadCustIndicators(InputStream inputStream) {
        List<String[]> csvData = fileReader.readCSV(inputStream);
        List<CopsCustIndicatorsSG> data = fileProcessor.processCustIndicators(csvData);
        fileWriter.writeCustIndicators(data);
    }
}
5. CSV Parser Utility
CSVParser.java

java
Copy code
package com.sc.faas.util;

import com.opencsv.CSVReader;
import javax.enterprise.context.ApplicationScoped;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.List;

@ApplicationScoped
public class CSVParser {
    public static List<String[]> parse(InputStream inputStream) {
        try (InputStreamReader isr = new InputStreamReader(inputStream);
             CSVReader csvReader = new CSVReader(isr)) {
            return csvReader.readAll();
        } catch (Exception e) {
            throw new RuntimeException("Error reading CSV file", e);
        }
    }
}
6. Controller
FileUploadResource.java

java
Copy code
package com.sc.faas.resource;

import com.sc.faas.service.FileUploadService;
import org.jboss.resteasy.reactive.MultipartForm;
import org.jboss.resteasy.reactive.RestForm;

import javax.inject.Inject;
import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.io.InputStream;

@Path("/file-upload")
public class FileUploadResource {

    @Inject
    FileUploadService fileUploadService;

    @POST
    @Path("/application-status")
    @Consumes(MediaType.MULTIPART_FORM_DATA)
    public Response uploadApplicationStatus(@MultipartForm FormData formData) {
        fileUploadService.uploadApplicationStatus(formData.file);
        return Response.ok("Application status file processed successfully").build();
    }

    @POST
    @Path("/cust-indicators")
    @Consumes(MediaType.MULTIPART_FORM_DATA)
    public Response uploadCustIndicators(@MultipartForm FormData formData) {
        fileUploadService.uploadCustIndicators(formData.file);
        return Response.ok("Customer indicators file processed successfully").build();
    }

    public static class FormData {
        @RestForm("file")
        public InputStream file;
    }
}
7. Configuration
application.properties

properties
Copy code
quarkus.datasource.db-kind=oracle
quarkus.datasource.username=<your-database-username>
quarkus.datasource.password=<your-database-password>
quarkus.datasource.jdbc.url=jdbc:oracle:thin:@<your-database-host>:<your-database-port>:<your-database-name>
quarkus.hibernate-orm.database.generation=none
Explanation
Entity Classes: CopsApplicationStatusSG and CopsCustIndicatorsSG map the CSV file data to the corresponding database tables.

Repository Interfaces: CopsApplicationStatusSGRepository and CopsCustIndicatorsSGRepository provide the CRUD operations for the entities.

Service Classes:

FileReader: Reads the CSV file and converts it to a list of string arrays.
FileProcessor: Converts the list of string arrays to a list of entity objects.
FileWriter: Persists the entity objects into the database.
FileUploadService: Coordinates the file reading, processing, and writing operations.
CSV Parser Utility: CSVParser uses OpenCSV to parse the CSV file.

Controller: FileUploadResource provides the REST endpoints for file uploads.

Configuration: The application.properties file contains the database connection settings.

Running the Application
Start the Quarkus application:

sh
Copy code
./mvnw compile quarkus:dev
Test the endpoints using a tool like curl or Postman:

Upload COPS_APPLICATION_STATUS_SG.csv:
sh
Copy code
curl -F file=@path/to/COPS_APPLICATION_STATUS_SG.csv http://localhost:8080/file-upload/application-status
Upload COPS_CUST_INDICATORS_SG.csv:
sh
Copy code
curl -F file=@path/to/COPS_CUST_INDICATORS_SG.csv http://localhost:8080/file-upload/cust-indicators
This setup allows you to upload two different CSV files, process their contents, and store the data into the corresponding tables in an Oracle database. Adjust the code as needed based on your specific requirements and CSV file structures.
