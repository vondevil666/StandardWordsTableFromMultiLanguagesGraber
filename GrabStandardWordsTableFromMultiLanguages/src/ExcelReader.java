import org.apache.poi.ss.usermodel.*;

import java.io.File;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;

/**
 * Created by dongz on 2017/7/25.
 */
public class ExcelReader {
    private static ArrayList<String> targetDataWeNeedList;
    private static ArrayList<ArrayList<String>> standardDataWeNeedList;

    //读取标准表文件，要求标准表中的辞林部分需要从D列开始向后延续存放
    public static ArrayList<HashSet<String>> readExcel(String standardExcelPath) {
        ArrayList<HashSet<String>> standardList = new ArrayList<HashSet<String>>();
        ArrayList<ArrayList<String>> standardDataWeNeedListTmp = new ArrayList<ArrayList<String>>();
        ArrayList<String> list;
        HashSet<String> set;
        try {
            Workbook wb = WorkbookFactory.create(new File(standardExcelPath));
            Sheet sheet = wb.getSheetAt(0);
            for (Row row : sheet) {
                if(row.getRowNum()==0)continue;
                list = new ArrayList<String>();
                set = new HashSet<String>();
                for (Cell cell : row) {
                    if(cell.getColumnIndex()<4)list.add(cell.toString());
                    else set.add(cell.toString());
                }
                standardDataWeNeedListTmp.add(list);
                standardList.add(set);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        standardDataWeNeedList=standardDataWeNeedListTmp;
        return standardList;
    }

    //对目标表的要求是，目标语言栏紧贴放在英语栏后面，英语栏若为D，则目标语言需要放在E
    public static ArrayList<String> readExcel(String targetExcelPath, int targetVolume) {
        ArrayList<String> targetEnglishList = new ArrayList<String>();
        ArrayList<String> targetDataWeNeedListTmp = new ArrayList<String>();
        try {
            Workbook wb = WorkbookFactory.create(new File(targetExcelPath));
            Sheet sheet = wb.getSheetAt(0);
            for (Row row : sheet) {
                if(row.getRowNum()==0)continue;
                for (Cell cell : row) {
                    if(cell.getColumnIndex()==targetVolume){
//                        cell.getStringCellValue();
                        targetEnglishList.add(cell.toString());
                    }
                    if(cell.getColumnIndex()==targetVolume+1){
                        targetDataWeNeedListTmp.add(cell.toString());
                        break;
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        targetDataWeNeedList=targetDataWeNeedListTmp;
        return targetEnglishList;
    }

    public static ArrayList<String> getTargetDataWeNeedList(){
        return targetDataWeNeedList;
    }

    public static ArrayList<ArrayList<String>> getStandardDataWeNeedList(){
        return standardDataWeNeedList;
    }
}
