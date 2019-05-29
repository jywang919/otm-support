package com.ori.rest.demo;

import java.io.File;
import java.io.FileOutputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletContext;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.ori.rest.SineWaveResultsData;


//@CrossOrigin(origins = "http://localhost:4200") //not getting here
//@RequestMapping(value = ExcelController.URL, method=RequestMethod.POST )


//@RestController
//@RequestMapping(value=ExcelController.URL)
public class ExcelController {
    private static final Logger LOG =
            LoggerFactory.getLogger(ExcelController.class);
    public static final String URL = "/r/excelupload";

    @Autowired
    private IExcelService service;    

    @Autowired
    ServletContext context;
    
    //src https://www.mkyong.com/spring-boot/spring-boot-file-upload-example-ajax-and-rest/
    @RequestMapping(method=RequestMethod.POST )
    public @ResponseBody Map<String, Object> uploadFile(
//    		@RequestBody Object obj
//    		@RequestParam(value="file", required = true)  MultipartFile file
//    		, @RequestParam(value = "data") String object
    		@RequestParam("file")  MultipartFile file
    		)
    {
     	
    	ExcelController.LOG.debug("-------------------ExcelController processing excel file"); 
    	
    	
//        Map<String,Object> map = new HashMap<String, Object>();        
//        try {
//            ObjectMapper mapper = new ObjectMapper();
//            map = mapper.readValue(object, new TypeReference<Map<String, String>>(){});
//        }catch (Exception ex){
//            ex.printStackTrace();
//        }

        String fileName = null;
        Map<String,Object> result = new HashMap<String, Object>();
        if (file != null && !file.isEmpty()) {
            try {

                fileName = file.getOriginalFilename();
                ExcelController.LOG.debug("fileName: "+fileName); 
                String folder = context.getRealPath("/resources");
                File f = new File(folder);
                if (!f.exists()) {
    				f.mkdir();    				
    			}
                f.setWritable(true);
                ExcelController.LOG.debug("folder : "+folder+ " exists: " +f.exists()); 
                
                folder = context.getRealPath("/resources/tmpimages");
                ExcelController.LOG.debug("working on folder : "+folder); 
                f = new File(folder);
                if (!f.exists()) {
    				f.mkdir();    				
    			}
                f.setWritable(true);
                ExcelController.LOG.debug("folder : "+folder+ " exists: " +f.exists()); 
                
                String fileNameWithPath = context.getRealPath("/resources/tmpimages/"+ fileName);
                ExcelController.LOG.debug("working on file : "+fileNameWithPath);
                f = new File(fileNameWithPath);
    			if (!f.exists()) {
    				f.createNewFile();    				
    			}
    			f.setWritable(true);
    			ExcelController.LOG.debug("fileName "+fileNameWithPath + " exists: "+f.exists()); 
    			
                FileOutputStream fos = new FileOutputStream(f);
                FileCopyUtils.copy(file.getBytes(), fos);
                
            } catch (Exception e) {
//                header.put(Utils.MESSAGE, "Image not uploaded! Exception occured!");
            	e.printStackTrace();
                return result;
            }
        }else{
        	ExcelController.LOG.debug("file is not present");
        }
        result.put("file", "resources/tmpimages/"+ fileName);
        return result;
    }
    
    @RequestMapping(method = RequestMethod.GET)    
    public @ResponseBody Map<String,Object>  getFiles(
     @RequestParam("r") final String role
    ) {
    	List<String> res = new ArrayList<String>();
    	Map<String,Object> result = new HashMap<String, Object>();    
    	File folder = new File(context.getRealPath("/resources/tmpimages"));
    	File[] listOfFiles = folder.listFiles();
        for (int i = 0; i < listOfFiles.length; i++) {
            if (listOfFiles[i].isFile()) {
//              System.out.println("File " + listOfFiles[i].getName());
              res.add(listOfFiles[i].getName());
            } else if (listOfFiles[i].isDirectory()) {
              System.out.println("Directory " + listOfFiles[i].getName());
            }
          }        
//    	res.add( "hello-1.txt");
//    	res.add( "hello-2.txt");
    	ExcelController.LOG.debug("getFiles using param r="+role);
    	result.put("files", res);
    	return result;
    	    
    }
}