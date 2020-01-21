

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.jxls.transformer.XLSTransformer;
import net.uzen.petra.common.constants.PetraConstants;
import net.uzen.petra.common.exception.PetraException;

import org.apache.commons.io.FilenameUtils;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.FileCopyUtils;
/**
 * 엑셀 다운로드 유틸클래스
 * 
 * @author YongJin
 * 
 */
public class ExcelUtil {

	private static final Logger logger = LoggerFactory.getLogger(ExcelUtil.class);

	/** 
	 * 
	 * @param request 
	 *            HttpServletRequest
	 * @param response
	 *            HttpServletResponse
	 * @param beans
	 *            excel에 담길 데이타
	 * @param templateFileName
	 *            템플릿 파일명
	 * @param downloadFileName
	 *            다운로드할 엑셀 파일명
	 * @throws Exception
	 */
	@SuppressWarnings({ "deprecation", "rawtypes" })
	public static void downloadExcel(HttpServletRequest request,HttpServletResponse response, Map beans, String templateFileName,String downloadFileName) throws Exception {

		String srcFile = request.getRealPath("") + "/WEB-INF/excelTemplate/"+ templateFileName;
		// .getServletContext().getRealPath("/") + "/WEB-INF/excelTemplate/"+
		// templateFileName;

		InputStream is = new FileInputStream(srcFile);

		XLSTransformer transformer = new XLSTransformer();
		Workbook workbook = transformer.transformXLS(is, beans);

		response.setHeader("Content-Transfer-Encoding", "binary;");
		response.setHeader("Pragma", "no-cache;");
		response.setHeader("Expires", "-1;");
		response.setHeader("Content-Disposition", "attachment;filename=\""+ java.net.URLEncoder.encode(downloadFileName, "UTF-8") + "\";");

		workbook.write(response.getOutputStream());
	}

	/**
	 * 엑셀데이터(파일)를 리스트 데이터로 반환
	 * 
	 * @param fileIn
	 *            InputStream
	 * @param startRow
	 *            데이터 시작 로우 0 base
	 * @param startCell
	 *            데이터 시작 컬럼 0 base
	 * @param colNames
	 *            컬럼 변수 명 배열 {"a","b","c"}
	 * @return
	 * @throws Exception
	 */
	public static List<PubMap> loadExcelToList(InputStream fileIn,int startRow, int startCell, String[] colNames) throws Exception {
		List<PubMap> list = new ArrayList<PubMap>();
		Workbook wb = null;

		if (fileIn == null || startRow < 0 || startCell < 0 || colNames == null
				|| colNames.length == 0) {
			return list;
		}

		try {
			wb = WorkbookFactory.create(fileIn); // new Workbook(fs);
		} catch (IOException e) {
			logger.error(Utils.getMethodName(), e);
			throw new PetraException(e, "엑셀파일 로딩 실패");
		} catch (InvalidFormatException e) {
			logger.error(Utils.getMethodName(), e);
			throw new PetraException(e, "엑셀파일 로딩 실패");
		} finally {// / IOException, InvalidFormatException
			if (fileIn != null) {
				fileIn.close();
			}
		}
		if (wb != null && wb.getSheetAt(0) != null) {
			Sheet sheet = wb.getSheetAt(0);

			int rowCnt = sheet.getPhysicalNumberOfRows();
			int colCnt = colNames.length;

			if (startRow > rowCnt) {
				logger.error(Utils.getMethodName(),	"엑셀파일 로딩 실패  startRow > rowCnt");
			}

			for (int rowIdx = startRow; rowIdx < rowCnt; rowIdx++) {
				// 행을 읽다
				Row xRow = sheet.getRow(rowIdx);
				if (xRow != null) {
					// 셀의 수
				int cellCnt = xRow.getPhysicalNumberOfCells();
				int notEmptyCnt = 0;
				PubMap rowData = new PubMap();
				for (int colIdx = 0, cellIdx = startCell; colIdx < colCnt; colIdx++, cellIdx++) {

					Object value = "";
					if (cellCnt > (cellIdx)) {
						// 셀값 읽기
						Cell xCell = xRow.getCell(cellIdx);
					if (xCell != null) {
						// 타입별로 내용 읽기
						switch (xCell.getCellType()) {

						case Cell.CELL_TYPE_BLANK:
							value = "";
							break;
						case Cell.CELL_TYPE_BOOLEAN:
							value = xCell.getBooleanCellValue();
							break;
						case Cell.CELL_TYPE_ERROR:
							value = xCell.getErrorCellValue();
							break;
						case Cell.CELL_TYPE_FORMULA:
							value = xCell.getCellFormula();
							break;
						case Cell.CELL_TYPE_NUMERIC:
							value = xCell.getNumericCellValue();
							break;
						case Cell.CELL_TYPE_STRING:
							value = xCell.getStringCellValue();
							break;
							default:
						// for inspection
								}
							}
						}
						rowData.put(colNames[colIdx], value);
						if (value != null
								&& StringUtil.isNotEmptyStr(value.toString())) {
							notEmptyCnt++; // 빈값이 아닌것
						}
					}
					if (!rowData.isEmpty() && notEmptyCnt > 0) { // 빈로우 추가 안함
						list.add(rowData);
					}
				}
			}

		} else {
			logger.error(Utils.getMethodName(), "엑셀파일 로딩 실패");
		}

		return list;
	}

	/**
	 * 텍스트화일 다운로드 생성
	 * 
	 * @throws Exception
	 */
	public static void FileTxtCreate(HttpServletRequest request,HttpServletResponse response, Map<String, Object> txtlist,String fileName) {

		try {
			// SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmss");
			// String todayMonth = sdf.format(new Date());

			// String fileName = "sourceDeployListCvs_"+todayMonth+".txt" ;
			String fileCont = "";
			String upDir = "/app/pub_fa/uploadTempDir/";
			String path = upDir + fileName;

			// Map<String, Object> map = new HashMap<String, Object>();
			// BufferedWriter 와 FileWriter를 조합하여 사용 (속도 향상)
			BufferedWriter fw = new BufferedWriter(new FileWriter(path, true));

			// 파일안에 문자열 쓰기
			@SuppressWarnings("unchecked")
			List<Map<String, Object>> list = (List<Map<String, Object>>) txtlist.get("list");
			//System.out.println(list);
			for (Map<String, Object> dlvVo : list) {
				fileCont = (String) dlvVo.get("filePath") + ","	+ (String) dlvVo.get("revisionNo") + ","+ (String) dlvVo.get("convFilePath")+ ","+ (String) dlvVo.get("filePate");
				fw.write(fileCont);
				fw.newLine();
				fw.flush();
			}
			// 객체 닫기
			fw.close();

			String serverFileName = fileName.replaceAll("\\.\\.", "xx"); // ..
																			// 제거\\..
			File file = new File(path);
			if (!file.exists()) {
				throw new Exception("파일이 존재하지 않습니다.");
			}

			response.setContentType(getContentType(FilenameUtils.getExtension(serverFileName)));
			setDisposition(fileName, request, response);
			FileCopyUtils.copy(new FileInputStream(file),response.getOutputStream());

		} catch (Exception e) {
			//e.printStackTrace();
			logger.debug("텍스트다운로드 FileTxtCreate",e);
		}
	}
	
	/*
	 * public static void FileTxtTestCreate(HttpServletRequest request,
	 * HttpServletResponse response, Map<String, Object> txtlist, String
	 * fileName) {
	 * 
	 * try{
	 * 
	 * String fileCont = "";
	 * 
	 * FileOutputStream fos = new FileOutputStream(fileName); OutputStreamWriter
	 * osw = new OutputStreamWriter(fos,"MS949"); BufferedWriter fw = new
	 * BufferedWriter(osw);
	 * 
	 * @SuppressWarnings("unchecked") List<Map<String, Object>> list =
	 * (List<Map<String, Object>>) txtlist.get("list");
	 * //System.out.println(list.toString()); for(Map<String, Object> dlvVo :
	 * list){
	 * 
	 * fileCont =
	 * (String)dlvVo.get("goodsNo")+","+(String)dlvVo.get("goodsNm")+","
	 * +(String)dlvVo.get("dispStrtDtime")+","+(String)dlvVo.get("dispEndDtime")
	 * +
	 * ","+(String)dlvVo.get("brndNo")+","+(String)dlvVo.get("brndNm")+","+(String
	 * )dlvVo.get("suppId")+","+(String)dlvVo.get("suppNm")
	 * +","+(String)dlvVo.get("goodsStatNm")+","+(String)dlvVo.get("salePrc");
	 * 
	 * fw.write(fileCont); fw.newLine(); fw.flush(); }
	 * 
	 * fw.close();
	 * 
	 * String serverFileName = fileName.replaceAll("\\.\\.", "xx"); File file =
	 * new File(fileName); if (!file.exists()){ throw new
	 * Exception("파일이 존재하지 않습니다."); }
	 * 
	 * response.setContentType(getContentType(FilenameUtils.getExtension(
	 * serverFileName))); setDisposition(fileName, request, response);
	 * 
	 * FileCopyUtils.copy(new FileInputStream(file),
	 * response.getOutputStream());
	 * 
	 * }catch(Exception e){ e.printStackTrace(); } }
	 */

	
	public static void FileTxtTestCreate(HttpServletRequest request,HttpServletResponse response, Map<String, Object> txtlist,String fileName) {

		try {

			// String fileCont = "";
			String baseDir = PetraConstants.getBaseDir();
			FileOutputStream fos = new FileOutputStream(baseDir+fileName);
			OutputStreamWriter osw = new OutputStreamWriter(fos, "MS949");
			BufferedWriter fw = new BufferedWriter(osw);

			@SuppressWarnings("unchecked")
			List<Map<String, String>> list = (List<Map<String, String>>) txtlist.get("list");

			for (Map<String, String> dlvVo : list) {
				StringBuffer sb = new StringBuffer();
				sb.append(dlvVo.get("goodsNo")).append(",")
				  .append(dlvVo.get("goodsNm")).append(",")
				  .append(dlvVo.get("dispStrtDtime")).append(",")
				  .append(dlvVo.get("dispEndDtime")).append(",")
				  .append(dlvVo.get("brndNo")).append(",")
				  .append(dlvVo.get("brndNm")).append(",")
				  .append(dlvVo.get("suppId")).append(",")
				  .append(dlvVo.get("suppNm")).append(",")
				  .append(dlvVo.get("goodsStatNm")).append(",")
				  .append(dlvVo.get("salePrc"));

				fw.write(sb.toString());
				fw.newLine();
				fw.flush();
			}

			fw.close();

			String serverFileName = fileName.replaceAll("\\.\\.", "xx");
			File file = new File(baseDir+fileName);
			if (!file.exists()) {
				throw new Exception("파일이 존재하지 않습니다.");
			}

			response.setContentType(getContentType(FilenameUtils.getExtension(serverFileName)));
			setDisposition(fileName, request, response);

			FileCopyUtils.copy(new FileInputStream(file),
					response.getOutputStream());

		} catch (Exception e) {
			//e.printStackTrace();
			logger.debug("텍스트다운로드 FileTxtCreate",e);
		}
	}

	//추천제외상품
	public static void FileTxtSelectExeRecom(HttpServletRequest request,HttpServletResponse response, Map<String, Object> txtlist,String fileName) {

		try {
			// String fileCont = "";
			FileOutputStream fos = new FileOutputStream(fileName);
			OutputStreamWriter osw = new OutputStreamWriter(fos, "MS949");
			BufferedWriter fw = new BufferedWriter(osw);
 
			@SuppressWarnings("unchecked")
			List<Map<String, String>> list = (List<Map<String, String>>) txtlist.get("list");
			
			for (Map<String, String> dlvVo : list) {
				StringBuffer sb = new StringBuffer();
				sb.append(dlvVo.get("goodsNo")).append(",").append(dlvVo.get("goodsNm")).append(",")
				  .append(dlvVo.get("exeNm")).append(",").append(dlvVo.get("caus")).append(",")
				  .append(dlvVo.get("salePrcCd")).append(",").append(dlvVo.get("salePrc")).append(",")
				  .append(dlvVo.get("mdId")).append(",").append(dlvVo.get("mdNm")).append(",")
				  .append(dlvVo.get("brndNo")).append(",").append(dlvVo.get("brndNm")).append(",")
				  .append(dlvVo.get("suppId")).append(",").append(dlvVo.get("suppNm")).append(",")
				  .append(dlvVo.get("regmnId")).append(",").append(dlvVo.get("sysRegDate")).append(",")
				  .append(dlvVo.get("modmnId")).append(",").append(dlvVo.get("sysModDate"));

				fw.write(sb.toString());
				fw.newLine();
				fw.flush();
			}

			fw.close();

			String serverFileName = fileName.replaceAll("\\.\\.", "xx");
			File file = new File(fileName);
			if (!file.exists()) {
				throw new Exception("파일이 존재하지 않습니다.");
			}

			response.setContentType(getContentType(FilenameUtils.getExtension(serverFileName)));
			setDisposition(fileName, request, response);

			FileCopyUtils.copy(new FileInputStream(file),response.getOutputStream());

		} catch (Exception e) {
			//e.printStackTrace();
			logger.debug("텍스트다운로드 FileTxtCreate",e);
		}
	}

	// 전사 상품분류 연결 다운로드
	public static void FileTxtClassCreate(HttpServletRequest request,HttpServletResponse response, Map<String, Object> txtlist,String fileName) {

		try {

			// String fileCont = "";
			String baseDir = PetraConstants.getBaseDir();
			FileOutputStream fos = new FileOutputStream(baseDir+fileName);
			OutputStreamWriter osw = new OutputStreamWriter(fos, "MS949");
			BufferedWriter fw = new BufferedWriter(osw);

			@SuppressWarnings("unchecked")
			List<Map<String, String>> list = (List<Map<String, String>>) txtlist.get("list");
			
			for (Map<String, String> dlvVo : list) {
				StringBuffer sb = new StringBuffer();
				sb.append(dlvVo.get("lcslId")).append(",")
				  .append(dlvVo.get("lcslNm")).append(",").append(dlvVo.get("mcslId")).append(",")
				  .append(dlvVo.get("mcslNm")).append(",").append(dlvVo.get("scslId")).append(",")
				  .append(dlvVo.get("scslNm")).append(",").append(dlvVo.get("prdClsId")).append(",")
				  .append(dlvVo.get("prdClsNm")).append(",").append(dlvVo.get("dispCatNo")).append(",")
				  .append(dlvVo.get("dispCatNm"));

				fw.write(sb.toString());
				fw.newLine();
				fw.flush();
			}

			fw.close();

			String serverFileName = fileName.replaceAll("\\.\\.", "xx");
			File file = new File(baseDir+fileName);
			if (!file.exists()) {
				throw new Exception("파일이 존재하지 않습니다.");
			}

			response.setContentType(getContentType(FilenameUtils.getExtension(serverFileName)));
			setDisposition(fileName, request, response);

			FileCopyUtils.copy(new FileInputStream(file),response.getOutputStream());

		} catch (Exception e) {
			//e.printStackTrace();
			logger.debug("텍스트다운로드 FileTxtCreate",e);
		}
	}

	// 상품별 카테고리 다운로드
	public static void FileTxtGoodsCategoryDisplayCreate(HttpServletRequest request, HttpServletResponse response,Map<String, Object> txtlist, String fileName) {

		try {

			// String fileCont = "";

			FileOutputStream fos = new FileOutputStream(fileName);
			OutputStreamWriter osw = new OutputStreamWriter(fos, "MS949");
			BufferedWriter fw = new BufferedWriter(osw);

			@SuppressWarnings("unchecked")
			List<Map<String, String>> list = (List<Map<String, String>>) txtlist.get("list");
			
			for (Map<String, String> dlvVo : list) {
				StringBuffer sb = new StringBuffer();
				sb.append(dlvVo.get("goodsNo")).append(",")
				  .append(dlvVo.get("goodsNm")).append(",").append(dlvVo.get("salePrc")).append(",")
				  .append(dlvVo.get("salePrcCd")).append(",").append(dlvVo.get("baseYn")).append(",")
				  .append(dlvVo.get("mdId")).append(",").append(dlvVo.get("mdNm")).append(",")
				  .append(dlvVo.get("suppId")).append(",").append(dlvVo.get("suppNm")).append(",")
				  .append(dlvVo.get("brndNo")).append(",").append(dlvVo.get("brndNm"));

				fw.write(sb.toString());
				fw.newLine();
				fw.flush();
			}

			fw.close();

			String serverFileName = fileName.replaceAll("\\.\\.", "xx");
			File file = new File(fileName);
			if (!file.exists()) {
				throw new Exception("파일이 존재하지 않습니다.");
			}

			response.setContentType(getContentType(FilenameUtils.getExtension(serverFileName)));
			setDisposition(fileName, request, response);

			FileCopyUtils.copy(new FileInputStream(file),
					response.getOutputStream());

		} catch (Exception e) {
			//e.printStackTrace();
			logger.debug("텍스트다운로드 FileTxtCreate",e);
		}
	}
	
	// 기획전 전시 다운로드
	public static void FileTxtEbtCsvListCreate(HttpServletRequest request, HttpServletResponse response,Map<String, Object> txtlist, String fileName) {

		try {

			// String fileCont = "";
			String baseDir = PetraConstants.getBaseDir();
			FileOutputStream fos = new FileOutputStream(baseDir+fileName);
			OutputStreamWriter osw = new OutputStreamWriter(fos, "MS949");
			BufferedWriter fw = new BufferedWriter(osw);

			@SuppressWarnings("unchecked")
			List<Map<String, String>> list = (List<Map<String, String>>) txtlist.get("list");
			
			for (Map<String, String> dlvVo : list) {
				StringBuffer sb = new StringBuffer();
				sb.append(dlvVo.get("ebtNo")).append(",").append(dlvVo.get("ebtNm")).append(",")
				  .append(dlvVo.get("ebtDescr")).append(",").append(dlvVo.get("ebtSctCd")).append(",")
				  .append(dlvVo.get("ebtSctNm")).append(",").append(dlvVo.get("tgtChNm")).append(",")
				  .append(dlvVo.get("dispYn")).append(",").append(dlvVo.get("ebtStatCd")).append(",")
				  .append(dlvVo.get("ebtStatNm")).append(",").append(dlvVo.get("dispSeqNo")).append(",")
				  .append(dlvVo.get("strDispStrtDate")).append(",").append(dlvVo.get("strDispEndDate")).append(",")
				  .append(dlvVo.get("mbrGrdNm")).append(",").append(dlvVo.get("dispCatNo")).append(",")
				  .append(dlvVo.get("dispCatNm")).append(",").append(dlvVo.get("sysRegMbrId")).append(",")
				  .append(dlvVo.get("sysRegMbrNm")).append(",").append(dlvVo.get("sysRegDate"));

				  fw.write(sb.toString());
				  fw.newLine();
				  fw.flush();
				}

				fw.close();

				String serverFileName = fileName.replaceAll("\\.\\.", "xx");
				File file = new File(baseDir+fileName);
				if (!file.exists()) {
					throw new Exception("파일이 존재하지 않습니다.");
				}

				response.setContentType(getContentType(FilenameUtils.getExtension(serverFileName)));
				setDisposition(fileName, request, response);
				FileCopyUtils.copy(new FileInputStream(file),response.getOutputStream());
				

			} catch (Exception e) {
				//e.printStackTrace();
				logger.debug("텍스트다운로드 FileTxtCreate",e);
			}
		}
	
	//상품평csv 다운로드
	public static void FileTxtgoodsAssmtCsvList(HttpServletRequest request, HttpServletResponse response,Map<String, Object> txtlist, String fileName) {

		try {
		// String fileCont = "";
		String baseDir = PetraConstants.getBaseDir();
		FileOutputStream fos = new FileOutputStream(baseDir+fileName);
		OutputStreamWriter osw = new OutputStreamWriter(fos, "MS949");
		BufferedWriter fw = new BufferedWriter(osw);

		@SuppressWarnings("unchecked")
		List<Map<String, String>> list = (List<Map<String, String>>) txtlist.get("list");

		for (Map<String, String> dlvVo : list) {
			StringBuffer sb = new StringBuffer(); 
			sb.append(dlvVo.get("goodsAssmtNo")).append(",").append(dlvVo.get("goodsNo")).append(",")
			  .append(dlvVo.get("goodsNm")).append(",").append(dlvVo.get("avgStfdVal")).append(",")
			  .append(dlvVo.get("stfdVal10")).append(",").append(dlvVo.get("stfdVal20")).append(",")
			  .append(dlvVo.get("stfdVal30")).append(",").append(dlvVo.get("stfdVal40")).append(",")
			  .append(dlvVo.get("premiumYn")).append(",").append(dlvVo.get("dispYn")).append(",")
			  .append(dlvVo.get("cont")).append(",").append(dlvVo.get("photoSysAppxFileNm")).append(",")
			  .append(dlvVo.get("email")).append(",").append(dlvVo.get("mbrNo")).append(",")
			  .append(dlvVo.get("mdNm")).append(",").append(dlvVo.get("regDtime"));

		fw.write(sb.toString());
		fw.newLine();
		fw.flush();
		}

		fw.close();

		String serverFileName = fileName.replaceAll("\\.\\.", "xx");
		File file = new File(baseDir+fileName);
		if (!file.exists()) {
			throw new Exception("파일이 존재하지 않습니다.");
		}

		response.setContentType(getContentType(FilenameUtils.getExtension(serverFileName)));
		setDisposition(fileName, request, response);

		FileCopyUtils.copy(new FileInputStream(file),response.getOutputStream());

		} catch (Exception e) {
			//e.printStackTrace();
			logger.debug("텍스트다운로드 FileTxtCreate",e);
		}
	}
	
	//상품평Mdcsv 다운로드
	public static void FileTxtgoodsAssmtMdCsvList(HttpServletRequest request, HttpServletResponse response,Map<String, Object> txtlist, String fileName) {

		try {
			// String fileCont = "";
			String baseDir = PetraConstants.getBaseDir();
			FileOutputStream fos = new FileOutputStream(baseDir+fileName);
			OutputStreamWriter osw = new OutputStreamWriter(fos, "MS949");
			BufferedWriter fw = new BufferedWriter(osw);

			@SuppressWarnings("unchecked")
			List<Map<String, String>> list = (List<Map<String, String>>) txtlist.get("list");
			
			for (Map<String, String> dlvVo : list) {
				StringBuffer sb = new StringBuffer(); 
				sb.append(dlvVo.get("goodsAssmtNo")).append(",").append(dlvVo.get("goodsNo")).append(",")
				  .append(dlvVo.get("goodsNm")).append(",").append(dlvVo.get("avgStfdVal")).append(",")
				  .append(dlvVo.get("stfdVal10")).append(",").append(dlvVo.get("stfdVal20")).append(",")
				  .append(dlvVo.get("stfdVal30")).append(",").append(dlvVo.get("stfdVal40")).append(",")
				  .append(dlvVo.get("premiumYn")).append(",").append(dlvVo.get("dispYn")).append(",")
				  .append(dlvVo.get("cont")).append(",").append(dlvVo.get("photoSysAppxFileNm")).append(",")
				  .append(dlvVo.get("email")).append(",").append(dlvVo.get("mbrNo")).append(",")
				  .append(dlvVo.get("mdNm")).append(",").append(dlvVo.get("regDtime"));

			fw.write(sb.toString());
			fw.newLine();
			fw.flush();
			}

			fw.close();

			String serverFileName = fileName.replaceAll("\\.\\.", "xx");
			File file = new File(baseDir+fileName);
			if (!file.exists()) {
				throw new Exception("파일이 존재하지 않습니다.");
			}

			response.setContentType(getContentType(FilenameUtils.getExtension(serverFileName)));
			setDisposition(fileName, request, response);

			FileCopyUtils.copy(new FileInputStream(file),response.getOutputStream());

			} catch (Exception e) {
				//e.printStackTrace();
				logger.debug("텍스트다운로드 FileTxtCreate",e);
			}
		}
	
	//FileTxtselectAlcCsvListCreate
	public static void FileTxtselectAlcCsvListCreate(HttpServletRequest request, HttpServletResponse response,Map<String, Object> txtlist, String fileName) {

		try {
			// String fileCont = "";
			String baseDir = PetraConstants.getBaseDir();
			FileOutputStream fos = new FileOutputStream(baseDir+fileName);
			OutputStreamWriter osw = new OutputStreamWriter(fos, "MS949");
			BufferedWriter fw = new BufferedWriter(osw);

			@SuppressWarnings("unchecked")
			List<Map<String, String>> list = (List<Map<String, String>>) txtlist.get("list");
			
			for (Map<String, String> dlvVo : list) {
				StringBuffer sb = new StringBuffer(); 
				sb.append(dlvVo.get("alcNo")).append(",").append(dlvVo.get("alcStatusNm")).append(",")
					.append(dlvVo.get("alcClsNm")).append(",").append(dlvVo.get("tit")).append(",")
					.append(dlvVo.get("alcCoNm")).append(",").append(dlvVo.get("alcBizerNo")).append(",")
					.append(dlvVo.get("pschNm")).append(",").append(dlvVo.get("pschEmail")).append(",")
					.append(dlvVo.get("pschTel")).append(",").append(dlvVo.get("pschCell")).append(",")
					.append(dlvVo.get("regDtime"));

			fw.write(sb.toString());
			fw.newLine();
			fw.flush();
			}

			fw.close();

			String serverFileName = fileName.replaceAll("\\.\\.", "xx");
			File file = new File(baseDir+fileName);
			if (!file.exists()) {
				throw new Exception("파일이 존재하지 않습니다.");
			}

			response.setContentType(getContentType(FilenameUtils.getExtension(serverFileName)));
			setDisposition(fileName, request, response);
			FileCopyUtils.copy(new FileInputStream(file),response.getOutputStream());
			if(file.exists()) file.delete();

			} catch (Exception e) {
				//e.printStackTrace();
				logger.debug("텍스트다운로드 FileTxtCreate",e);
			}
		}
	
	//FileTxtEbtStatsCsvCreate
	public static void FileTxtEbtStatsCsvCreate(HttpServletRequest request, HttpServletResponse response,Map<String, Object> txtlist, String fileName) {

		try {
			// String fileCont = "";
			String baseDir = PetraConstants.getBaseDir();
			FileOutputStream fos = new FileOutputStream(baseDir+fileName);
			OutputStreamWriter osw = new OutputStreamWriter(fos, "MS949");
			BufferedWriter fw = new BufferedWriter(osw);

			@SuppressWarnings("unchecked")
			List<Map<String, String>> list = (List<Map<String, String>>) txtlist.get("list");
			
			for (Map<String, String> dlvVo : list) {
				StringBuffer sb = new StringBuffer(); 
				sb.append(dlvVo.get("dispCatNo")).append(",").append(dlvVo.get("dispCatNm")).append(",")
					.append(dlvVo.get("catCnt")).append(",").append(dlvVo.get("goodsCnt"));

				fw.write(sb.toString());
				fw.newLine();
				fw.flush();
				}

				fw.close();

				String serverFileName = fileName.replaceAll("\\.\\.", "xx");
				File file = new File(baseDir+fileName);
				if (!file.exists()) {
					throw new Exception("파일이 존재하지 않습니다.");
				}

				response.setContentType(getContentType(FilenameUtils.getExtension(serverFileName)));
				setDisposition(fileName, request, response);

				FileCopyUtils.copy(new FileInputStream(file),response.getOutputStream());

				} catch (Exception e) {
					//e.printStackTrace();
					logger.debug("텍스트다운로드 FileTxtCreate",e);
				}
			}
		
	//FileTxtdispCatStatsCsvCreate
	public static void FileTxtdispCatStatsCsvCreate(HttpServletRequest request, HttpServletResponse response,Map<String, Object> txtlist, String fileName) {

		try {
				// String fileCont = "";
			String baseDir = PetraConstants.getBaseDir();
			FileOutputStream fos = new FileOutputStream(baseDir+fileName);
			OutputStreamWriter osw = new OutputStreamWriter(fos, "MS949");
			BufferedWriter fw = new BufferedWriter(osw);

			@SuppressWarnings("unchecked")
			List<Map<String, String>> list = (List<Map<String, String>>) txtlist.get("list");
			
			for (Map<String, String> dlvVo : list) {
				StringBuffer sb = new StringBuffer(); 
				sb.append(dlvVo.get("prdLclsId")).append(",").append(dlvVo.get("prdLclsNm")).append(",")
					.append(dlvVo.get("disp1LclsCnt")).append(",").append(dlvVo.get("disp2LclsCnt")).append(",")
					.append(dlvVo.get("hapLclsCnt")).append(",").append(dlvVo.get("prdMclsId")).append(",")
					.append(dlvVo.get("prdMclsNm")).append(",").append(dlvVo.get("disp1SclsCnt")).append(",")
					.append(dlvVo.get("disp2MclsCnt")).append(",").append(dlvVo.get("hapMclsCnt")).append(",")
					.append(dlvVo.get("prdSclsId")).append(",").append(dlvVo.get("prdSclsNm")).append(",")
					.append(dlvVo.get("disp1SclsCnt")).append(",").append(dlvVo.get("disp2SclsCnt")).append(",")
					.append(dlvVo.get("hapSclsCnt")).append(",").append(dlvVo.get("prdDclsId")).append(",")
					.append(dlvVo.get("prdDclsNm")).append(",").append(dlvVo.get("disp1DclsCnt")).append(",")
					.append(dlvVo.get("disp2DclsCnt")).append(",").append(dlvVo.get("hapDclsCnt"));

					fw.write(sb.toString());
					fw.newLine();
					fw.flush();
				}

					fw.close();

					String serverFileName = fileName.replaceAll("\\.\\.", "xx");
					File file = new File(baseDir+fileName);
					if (!file.exists()) {
						throw new Exception("파일이 존재하지 않습니다.");
					}

					response.setContentType(getContentType(FilenameUtils.getExtension(serverFileName)));
					setDisposition(fileName, request, response);

					FileCopyUtils.copy(new FileInputStream(file),response.getOutputStream());

					} catch (Exception e) {
							//e.printStackTrace();
						logger.debug("텍스트다운로드 FileTxtCreate",e);
				}
			}
	
	//FileTxtbrcntcStatsCsvCreate
	public static void FileTxtbrcntcStatsCsvCreate(HttpServletRequest request, HttpServletResponse response,Map<String, Object> txtlist, String fileName) {

		try {
			// String fileCont = "";
			String baseDir = PetraConstants.getBaseDir();
			FileOutputStream fos = new FileOutputStream(baseDir+fileName);
			OutputStreamWriter osw = new OutputStreamWriter(fos, "MS949");
			BufferedWriter fw = new BufferedWriter(osw);

			@SuppressWarnings("unchecked")
			List<Map<String, String>> list = (List<Map<String, String>>) txtlist.get("list");
			
			for (Map<String, String> dlvVo : list) {
				StringBuffer sb = new StringBuffer(); 
				sb.append(dlvVo.get("goodsNo")).append(",").append(dlvVo.get("prdNm")).append(",")
					.append(dlvVo.get("onlBrcntcRcvCntOne")).append(",").append(dlvVo.get("onlBrcntcRcvCntDaily")).append(",")
					.append(dlvVo.get("onlBrcntcRcvTerm10Day")).append(",").append(dlvVo.get("onlBrcntcRcvTerm20Day")).append(",")
					.append(dlvVo.get("onlBrcntcRcvTerm30Day")).append(",").append(dlvVo.get("onlBrcntcRcvHap")).append(",")
					.append(dlvVo.get("ctrBrcntcRcvCntOne")).append(",").append(dlvVo.get("ctrBrcntcRcvCntDaily")).append(",")
					.append(dlvVo.get("ctrBrcntcRcvTerm10Day")).append(",").append(dlvVo.get("ctrBrcntcRcvTerm20Day")).append(",")
					.append(dlvVo.get("ctrBrcntcRcvTerm30Day")).append(",").append(dlvVo.get("ctrBrcntcRcvHap")).append(",")
					.append(dlvVo.get("totBrcntcRcvTotal"));

					fw.write(sb.toString());
					fw.newLine();
					fw.flush();
					}

					fw.close();

					String serverFileName = fileName.replaceAll("\\.\\.", "xx");
					File file = new File(baseDir+fileName);
					if (!file.exists()) {
						throw new Exception("파일이 존재하지 않습니다.");
					}

					response.setContentType(getContentType(FilenameUtils.getExtension(serverFileName)));
					setDisposition(fileName, request, response);

					FileCopyUtils.copy(new FileInputStream(file),response.getOutputStream());

					} catch (Exception e) {
						//e.printStackTrace();
						logger.debug("텍스트다운로드 FileTxtCreate",e);
					}
			}
	
	//FileTxtbrcntCatStatsCsvCreate
	public static void FileTxtbrcntCatStatsCsvCreate(HttpServletRequest request, HttpServletResponse response,Map<String, Object> txtlist, String fileName) {

		try {
			// String fileCont = "";
			String baseDir = PetraConstants.getBaseDir();
			FileOutputStream fos = new FileOutputStream(baseDir+fileName);
			OutputStreamWriter osw = new OutputStreamWriter(fos, "MS949");
			BufferedWriter fw = new BufferedWriter(osw);

			@SuppressWarnings("unchecked")
			List<Map<String, String>> list = (List<Map<String, String>>) txtlist.get("list");
						
			for (Map<String, String> dlvVo : list) {
				StringBuffer sb = new StringBuffer(); 
				sb.append(dlvVo.get("prdLclsId")).append(",").append(dlvVo.get("prdLclsNm")).append(",")
				  .append(dlvVo.get("prdMclsId")).append(",").append(dlvVo.get("prdMclsNm")).append(",")
				  .append(dlvVo.get("prdSclsId")).append(",").append(dlvVo.get("prdSclsNm")).append(",")
				  .append(dlvVo.get("onlBrcntcRcvCntOne")).append(",").append(dlvVo.get("onlBrcntcRcvCntDaily")).append(",")
				  .append(dlvVo.get("onlBrcntcRcvTerm10Day")).append(",").append(dlvVo.get("onlBrcntcRcvTerm20Day")).append(",")
				  .append(dlvVo.get("onlBrcntcRcvTerm30Day")).append(",").append(dlvVo.get("onlBrcntcRcvHap")).append(",")
				  .append(dlvVo.get("ctrBrcntcRcvCntOne")).append(",").append(dlvVo.get("ctrBrcntcRcvCntDaily")).append(",")
				  .append(dlvVo.get("ctrBrcntcRcvTerm10Day")).append(",").append(dlvVo.get("ctrBrcntcRcvTerm20Day")).append(",")
				  .append(dlvVo.get("ctrBrcntcRcvTerm30Day")).append(",").append(dlvVo.get("ctrBrcntcRcvHap")).append(",")
				  .append(dlvVo.get("totBrcntcRcvTotal"));
								
				fw.write(sb.toString());
				fw.newLine();
				fw.flush();
				}

				fw.close();

				String serverFileName = fileName.replaceAll("\\.\\.", "xx");
				File file = new File(baseDir+fileName);
				if (!file.exists()) {
					throw new Exception("파일이 존재하지 않습니다.");
				}

				response.setContentType(getContentType(FilenameUtils.getExtension(serverFileName)));
				setDisposition(fileName, request, response);

				FileCopyUtils.copy(new FileInputStream(file),response.getOutputStream());

				} catch (Exception e) {
					//e.printStackTrace();
					logger.debug("텍스트다운로드 FileTxtCreate",e);
				}
		}
	
	//FileTxtbrcntBrandStatsCsvListCreate
	public static void FileTxtbrcntBrandStatsCsvListCreate(HttpServletRequest request, HttpServletResponse response,Map<String, Object> txtlist, String fileName) {

			try {
				// String fileCont = "";
				String baseDir = PetraConstants.getBaseDir();
				FileOutputStream fos = new FileOutputStream(baseDir+fileName);
				OutputStreamWriter osw = new OutputStreamWriter(fos, "MS949");
				BufferedWriter fw = new BufferedWriter(osw);

				@SuppressWarnings("unchecked")
				List<Map<String, String>> list = (List<Map<String, String>>) txtlist.get("list");
				
				for (Map<String, String> dlvVo : list) {
					StringBuffer sb = new StringBuffer(); 
					sb.append(dlvVo.get("goodsNo")).append(",").append(dlvVo.get("brndNm")).append(",")
						.append(dlvVo.get("onlBrcntcRcvCntOne")).append(",").append(dlvVo.get("onlBrcntcRcvCntDaily")).append(",")
						.append(dlvVo.get("onlBrcntcRcvTerm10Day")).append(",").append(dlvVo.get("onlBrcntcRcvTerm20Day")).append(",")
						.append(dlvVo.get("onlBrcntcRcvTerm30Day")).append(",").append(dlvVo.get("onlBrcntcRcvHap")).append(",")
						.append(dlvVo.get("ctrBrcntcRcvCntOne")).append(",").append(dlvVo.get("ctrBrcntcRcvCntDaily")).append(",")
						.append(dlvVo.get("ctrBrcntcRcvTerm10Day")).append(",").append(dlvVo.get("ctrBrcntcRcvTerm20Day")).append(",")
						.append(dlvVo.get("ctrBrcntcRcvTerm30Day")).append(",").append(dlvVo.get("ctrBrcntcRcvHap")).append(",")
						.append(dlvVo.get("totBrcntcRcvTotal"));
									
						fw.write(sb.toString());
						fw.newLine();
						fw.flush();
						}
						fw.close();

						String serverFileName = fileName.replaceAll("\\.\\.", "xx");
						File file = new File(baseDir+fileName);
						if (!file.exists()) {
							throw new Exception("파일이 존재하지 않습니다.");
						}

						response.setContentType(getContentType(FilenameUtils.getExtension(serverFileName)));
						setDisposition(fileName, request, response);

						FileCopyUtils.copy(new FileInputStream(file),response.getOutputStream());

						} catch (Exception e) {
							//e.printStackTrace();
							logger.debug("텍스트다운로드 FileTxtCreate",e);
						}
			}
	
	//FileTxtbrcntBrandStatsCsvListCreate
	public static void FileTxtGoodsRetrvStatsCsvCreate(HttpServletRequest request, HttpServletResponse response,Map<String, Object> txtlist, String fileName) {

		try {
		// String fileCont = "";
			String baseDir = PetraConstants.getBaseDir();
			FileOutputStream fos = new FileOutputStream(baseDir+fileName);
			OutputStreamWriter osw = new OutputStreamWriter(fos, "MS949");
			BufferedWriter fw = new BufferedWriter(osw);

			@SuppressWarnings("unchecked")
			List<Map<String, String>> list = (List<Map<String, String>>) txtlist.get("list");
				
			for (Map<String, String> dlvVo : list) {
				StringBuffer sb = new StringBuffer(); 
				sb.append(dlvVo.get("goodsNo")).append(",").append(dlvVo.get("goodsNm")).append(",")
				  .append(dlvVo.get("gubunNm")).append(",").append(dlvVo.get("goodsRegDtime")).append(",")
				  .append(dlvVo.get("regDtime")).append(",").append(dlvVo.get("saleQty")).append(",")
				  .append(dlvVo.get("clckCnt")).append(",").append(dlvVo.get("goodsAssmtCnt"));
										
				  fw.write(sb.toString());
				  fw.newLine();
				  fw.flush();
			}
				  fw.close();

				 String serverFileName = fileName.replaceAll("\\.\\.", "xx");
				  File file = new File(baseDir+fileName);
					if (!file.exists()) {
						throw new Exception("파일이 존재하지 않습니다.");
					}

				 response.setContentType(getContentType(FilenameUtils.getExtension(serverFileName)));
				 setDisposition(fileName, request, response);

				 FileCopyUtils.copy(new FileInputStream(file),response.getOutputStream());
 					} catch (Exception e) {
						//e.printStackTrace();
 						logger.debug("텍스트다운로드 FileTxtCreate",e);
					}
			}
	
	//FileTxtsnsInfoListCsvCreate
	public static void FileTxtsnsInfoListCsvCreate(HttpServletRequest request, HttpServletResponse response,Map<String, Object> txtlist, String fileName) {

		try {
		// String fileCont = "";
			String baseDir = PetraConstants.getBaseDir();
			FileOutputStream fos = new FileOutputStream(baseDir+fileName);
			OutputStreamWriter osw = new OutputStreamWriter(fos, "MS949");
			BufferedWriter fw = new BufferedWriter(osw);

			@SuppressWarnings("unchecked")
			List<Map<String, String>> list = (List<Map<String, String>>) txtlist.get("list");
			//System.out.println(list.toString());		
			for (Map<String, String> dlvVo : list) {
				StringBuffer sb = new StringBuffer(); 
				sb.append(dlvVo.get("baseDate")).append(",").append(dlvVo.get("chDtlId")).append(",")
				  .append(dlvVo.get("newCstCnt")).append(",").append(dlvVo.get("oldCstCnt")).append(",")
				  .append(dlvVo.get("outCstCnt")).append(",").append(dlvVo.get("dayTotCnt")).append(",")
				  .append(dlvVo.get("accCstCnt")).append(",").append(dlvVo.get("accOutCstCnt")).append(",")
				  .append(dlvVo.get("accTotCnt"));							
				  fw.write(sb.toString());
				  fw.newLine();
				  fw.flush();
			}
				  fw.close();

			      String serverFileName = fileName.replaceAll("\\.\\.", "xx");
			      File file = new File(baseDir+fileName);
					if (!file.exists()) {
						throw new Exception("파일이 존재하지 않습니다.");
					}

				  response.setContentType(getContentType(FilenameUtils.getExtension(serverFileName)));
				  setDisposition(fileName, request, response);

				  FileCopyUtils.copy(new FileInputStream(file),response.getOutputStream());
	 				} catch (Exception e) {
					  //e.printStackTrace();
	 					logger.debug("텍스트다운로드 FileTxtCreate",e);
					}
			}
	
	
	//이벤트 응모자
	public static void FileTxtEventApplcnCsvListCreate(HttpServletRequest request, HttpServletResponse response, String fileName) {

		try {
				// String fileCont = "";
			FileOutputStream fos = new FileOutputStream(fileName,true);

			String serverFileName = fileName.replaceAll("\\.\\.", "xx");
			File file = new File(fileName);
			if (!file.exists()) {
				throw new Exception("파일이 존재하지 않습니다.");
				}
			
				response.setContentType(getContentType(FilenameUtils.getExtension(serverFileName)));
				setDisposition(fileName, request, response);
			
				FileCopyUtils.copy(new FileInputStream(file),response.getOutputStream());
			
				} catch (Exception e) {
					//e.printStackTrace();
					logger.debug("텍스트다운로드 FileTxtCreate",e);
				}
		}
	
	public static void FileAppliCsv(Map<String, Object> txtlist, String fileName){
	
		FileWriter fw =null;
		BufferedWriter bw =null;
		PrintWriter out = null;

		
		
		try{
			FileOutputStream fos = new FileOutputStream(fileName,true);
			//OutputStreamWriter osw = new OutputStreamWriter(fos, "MS949");
			//BufferedWriter fw = new BufferedWriter(osw);	
			File file = new File(fileName);
	

			if (!file.exists()) {
			    file.createNewFile();
			    System.out.println("===========create file ==========");
			}else{
				System.out.println(file.length());
			}
		
			fw = new FileWriter(file,true);
			bw = new BufferedWriter(new OutputStreamWriter (fos,"EUC-KR"));
		
				
			//fw = new FileWriter(fileName, true);
			//bw = new BufferedWriter(fw);
			//out = new PrintWriter(bw);
			List<Map<String, String>> list  = (List<Map<String, String>>) txtlist.get("list");
						
			for (Map<String, String> dlvVo : list) {
				StringBuffer sb = new StringBuffer(); 
				sb.append(dlvVo.get("no")).append(",").append(dlvVo.get("mbrNo")).append(",")
				   .append(dlvVo.get("mbrNm")).append(",").append(dlvVo.get("applcnDtime"));		
				
				bw.write(sb.toString()+"\n");
				bw.flush();
		
										
			}
			
			
		}catch(IOException e){
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			//e.printStackTrace();
			logger.debug("텍스트다운로드 FileTxtCreate",e);
		}finally{
			try{
				if(out !=null){
					out.close();
				}
				else if(bw!=null){
					bw.close();
				}
				else if(fw!=null){
					fw.close();
				}
				else{
					
				}
			
			}catch (IOException e){
			}
		}
	}

	//MyConfim
	public static void FileTxtMyConfimCsvCreate(HttpServletRequest request, HttpServletResponse response,Map<String, Object> txtlist, String fileName) {
		
			try {
				// String fileCont = "";
				FileOutputStream fos = new FileOutputStream(fileName);
				OutputStreamWriter osw = new OutputStreamWriter(fos, "MS949");
				BufferedWriter fw = new BufferedWriter(osw);
		
				@SuppressWarnings("unchecked")
				List<Map<String, String>> list  = (List<Map<String, String>>) txtlist.get("list");
												
				for (Map<String, String> dlvVo : list) {
					StringBuffer sb = new StringBuffer(); 
					sb.append(dlvVo.get("certCorpSctNm")).append(",").append(dlvVo.get("cnt")).append(",")
					  .append(dlvVo.get("total"));							
					  fw.write(sb.toString());
					  fw.newLine();
					  fw.flush();
												
					}
						
										
					  fw.close();
		
					  String serverFileName = fileName.replaceAll("\\.\\.", "xx");
					  File file = new File(fileName);
						if (!file.exists()) {
							throw new Exception("파일이 존재하지 않습니다.");
						}
		
					  response.setContentType(getContentType(FilenameUtils.getExtension(serverFileName)));
					  setDisposition(fileName, request, response);
		
					  FileCopyUtils.copy(new FileInputStream(file),response.getOutputStream());
		
					  } catch (Exception e) {
						//e.printStackTrace();
						  logger.debug("텍스트다운로드 FileTxtCreate",e);
					  }
	}
		
	
	private static String getContentType(String extention) {
		String contentFileType = null;
		if (extention == null || extention.equals("")) {
			contentFileType = "applicationxxxxxxxxxx";
		} else if ((extention).equals("bmp")) {
			contentFileType = "image/bmp";
		} else if ((extention).equals("doc")) {
			contentFileType = "application/msword";
		} else if ((extention).equals("html")) {
			contentFileType = "text/html";
		} else if ((extention).equals("hwp")) {
			contentFileType = "application";
		} else if ((extention).equals("jpg")) {
			contentFileType = "image/jpeg";
		} else if ((extention).equals("mid")) {
			contentFileType = "audio/midi";
		} else if ((extention).equals("mp3")) {
			contentFileType = "audio/x-mp3";
		} else if ((extention).equals("mpeg")) {
			contentFileType = "video/mpeg";
		} else if ((extention).equals("pdf")) {
			contentFileType = "application/pdf";
		} else if ((extention).equals("ppt")) {
			contentFileType = "application/vnd.ms-powerpoint";
		} else if ((extention).equals("pptx")) {
			contentFileType = "application/vnd.ms-powerpoint";
		} else if ((extention).equals("ra")) {
			contentFileType = "audio/x-pn-realaudio";
		} else if ((extention).equals("txt")) {
			contentFileType = "text/plain";
		} else if ((extention).equals("xls")) {
			contentFileType = "application/vnd.ms-excel";
		} else {
			contentFileType = "applicationxxxxxxxxxx";
		}
		return contentFileType;
	}

	/**
	 * Disposition 지정하기.
	 * 
	 * @param filename
	 * @param request
	 * @param response
	 * @throws Exception
	 */
	private static void setDisposition(String filename,HttpServletRequest request, HttpServletResponse response)throws Exception {
		String browser = getBrowser(request);

		String dispositionPrefix = "attachment; filename=";
		String encodedFilename = null;

		if (browser.equals("Trident")) { // ie 11
			encodedFilename = URLEncoder.encode(filename, "UTF-8").replaceAll( "\\+", "%20");
		} else if (browser.equals("MSIE")) {
			encodedFilename = URLEncoder.encode(filename, "UTF-8").replaceAll( "\\+", "%20");
		} else if (browser.equals("Firefox")) {
			// encodedFilename = "\"" + new String(filename.getBytes("UTF-8"),
			// "8859_1") + "\"";
			logger.debug("browser=Firefox");
		} else if (browser.equals("Opera")) {
			encodedFilename = "\"" + new String(filename.getBytes("UTF-8"), "8859_1") + "\"";
		} else if (browser.equals("Chrome")) {
			StringBuffer sb = new StringBuffer();
			for (int i = 0; i < filename.length(); i++) {
				char c = filename.charAt(i);
				if (c > '~') {
					sb.append(URLEncoder.encode("" + c, "UTF-8"));
				} else {
					sb.append(c);
				}
			}
			encodedFilename = sb.toString();
		} else {
			// throw new RuntimeException("Not supported browser");
			throw new IOException("Not supported browser");
		}

		// response.setHeader("Content-Disposition", dispositionPrefix +
		// encodedFilename);
		if (browser.equals("Firefox") || browser.equals("Opera")) {
			response.setHeader("Content-Disposition", dispositionPrefix + "\"" + filename + "\"");
		} else {
			response.setHeader("Content-Disposition", dispositionPrefix	+ encodedFilename);
		}

	}

	/**
	 * 브라우저 구분 얻기.
	 * 
	 * @param request
	 * @return
	 */
	private static String getBrowser(HttpServletRequest request) {
		String header = request.getHeader("User-Agent");
		if (header.indexOf("Trident") > -1) { // ie 11 에서 MSIE가 삭제됨
			return "Trident";
		} else if (header.indexOf("MSIE") > -1) {
			return "MSIE";
		} else if (header.indexOf("Chrome") > -1) {
			return "Chrome";
		} else if (header.indexOf("Opera") > -1) {
			return "Opera";
		}
		return "Firefox";
	}
	
	public static void readWrite(InputStream is,ServletOutputStream out,BufferedInputStream bis,BufferedOutputStream bos){
	    try {
	      bis = new BufferedInputStream(is);
	      bos = new BufferedOutputStream(out);
	      byte[] buff = new byte[2048];
	      int bytesRead;
	      // Simple read/write loop.
	      while (-1 != (bytesRead = bis.read(buff, 0, buff.length))) {
	        bos.write(buff, 0, bytesRead);
	      }
	      bos.flush();
	    } catch (final IOException e) {
	       //e.printStackTrace();
	    	 logger.debug("엑셀다운로드 readWrite",e);	
	    }
	  }
	
	
	/**
	 * 엑셀데이터(파일)를 리스트 데이터로 반환
	 * 
	 * @param fileIn
	 *            InputStream
	 * @param startRow
	 *            데이터 시작 로우 0 base
	 * @param startCell
	 *            데이터 시작 컬럼 0 base
	 * @param colNames
	 *            컬럼 변수 명 배열 {"a","b","c"}
	 * @return
	 * @throws Exception
	 */
	public static List<PubMap> loadExcelList(InputStream fileIn,int startRow, int startCell, String[] colNames) throws Exception {
		List<PubMap> list = new ArrayList<PubMap>();
		Workbook wb = null;

		if (fileIn == null || startRow < 0 || startCell < 0 || colNames == null
				|| colNames.length == 0) {
			return list;
		}

		try {
			wb = WorkbookFactory.create(fileIn); // new Workbook(fs);
		} catch (IOException e) {
			logger.error(Utils.getMethodName(), e);
			throw new PetraException(e, "엑셀파일 로딩 실패");
		} catch (InvalidFormatException e) {
			logger.error(Utils.getMethodName(), e);
			throw new PetraException(e, "엑셀파일 로딩 실패");
		} finally {// / IOException, InvalidFormatException
			if (fileIn != null) {
				fileIn.close();
			}
		}
		if (wb != null && wb.getSheetAt(0) != null) {
			Sheet sheet = wb.getSheetAt(0);

			int rowCnt = sheet.getPhysicalNumberOfRows();
			int colCnt = colNames.length;

			if (startRow > rowCnt) {
				logger.error(Utils.getMethodName(),	"엑셀파일 로딩 실패  startRow > rowCnt");
			}

			for (int rowIdx = startRow; rowIdx < rowCnt; rowIdx++) {
				// 행을 읽다
				Row xRow = sheet.getRow(rowIdx);
				if (xRow != null) {
					// 셀의 수
				int cellCnt = xRow.getPhysicalNumberOfCells();
				int notEmptyCnt = 0;
				PubMap rowData = new PubMap();
				for (int colIdx = 0, cellIdx = startCell; colIdx < colCnt; colIdx++, cellIdx++) {

					Object value = "";
					if (cellCnt > (cellIdx)) {
						// 셀값 읽기
						Cell xCell = xRow.getCell(cellIdx);
					if (xCell != null) {
						// 타입별로 내용 읽기
						switch (xCell.getCellType()) {

						case Cell.CELL_TYPE_BLANK:
							value = "";
							break;
						case Cell.CELL_TYPE_BOOLEAN:
							value = xCell.getBooleanCellValue();
							break;
						case Cell.CELL_TYPE_ERROR:
							value = xCell.getErrorCellValue();
							break;
						case Cell.CELL_TYPE_FORMULA:
							value = xCell.getCellFormula();
							break;
						case Cell.CELL_TYPE_NUMERIC:
							value = (int)xCell.getNumericCellValue();
							break;
						case Cell.CELL_TYPE_STRING:
							value = xCell.getStringCellValue();
							break;
							default:
						// for inspection
								}
							}
						}
						rowData.put(colNames[colIdx], value);
						if (value != null
								&& StringUtil.isNotEmptyStr(value.toString())) {
							notEmptyCnt++; // 빈값이 아닌것
						}
					}
					if (!rowData.isEmpty() && notEmptyCnt > 0) { // 빈로우 추가 안함
						list.add(rowData);
					}
				}
			}

		} else {
			logger.error(Utils.getMethodName(), "엑셀파일 로딩 실패");
		}

		return list;
	}

}


