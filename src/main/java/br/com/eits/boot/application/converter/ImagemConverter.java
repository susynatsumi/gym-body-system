package br.com.eits.boot.application.converter;

import java.io.IOException;
import java.io.InputStream;

import org.apache.commons.io.IOUtils;
import org.directwebremoting.io.FileTransfer;
import org.springframework.stereotype.Component;

@Component
public class ImagemConverter {

	/**
	 * converte o objeto do tipo file Transfer populado no form do front end para array de bytes 
	 * para que seja possível realizar a persistência
	 * @param fileTransfer
	 * @return
	 */
	public byte[] fileTransferToByteArray(FileTransfer fileTransfer){
			
		if(fileTransfer == null){
			return null;
		}
		
		try {
			InputStream input = fileTransfer.getInputStream();
			return IOUtils.toByteArray(input);
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		return null;
			
	}
	
//	public FileTransfer byteArrayToFileTransfer(String fileName, String mimeType, byte[] byteArray) throws IOException{
//		
//		if( byteArray == null ){
//			return null;
//		}
//		
//		return new FileTransfer(fileName, mimeType, byteArray);
//		
//	}
	
}
