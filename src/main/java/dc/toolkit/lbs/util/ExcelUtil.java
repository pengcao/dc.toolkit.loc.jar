/**
 * Description: 
 * Author: caopeng
 * Creation time: 2016年4月6日 下午7:19:29
 * (C) Copyright 2013-2016, deamoncao.
 * All rights reserved.
 */
package dc.toolkit.lbs.util;

import org.apache.poi.hssf.usermodel.HSSFCell;

public class ExcelUtil {
	
	/**
	 * 返回Excel2003里面单元的值,一个单元格里面包含有很多类型的值,这里面的值,有String类型、Boolean、Numeric等类型的值
	 * 把这些值全部转换成String类型进行返回
	 * Description: 
	 * Author: caopeng
	 * Creation time: 2016年4月6日 下午7:22:07
	 *
	 * @param hssfCell
	 * @return
	 */
	public static String getExcel2003CellValue(HSSFCell hssfCell) {
		if(hssfCell==null){
			return "";
		}else{
			int cellType = hssfCell.getCellType();
			if (cellType == hssfCell.CELL_TYPE_STRING) {
				return String.valueOf(hssfCell.getStringCellValue());
			} else if (cellType == hssfCell.CELL_TYPE_BOOLEAN) {
				return String.valueOf(hssfCell.getBooleanCellValue());
			} else if (cellType == hssfCell.CELL_TYPE_NUMERIC) {
				return String.valueOf(hssfCell.getNumericCellValue());
			} else {
				return "";
			}
		}

	}

}
