import java.util.ArrayList;
import java.util.HashSet;

/**
 * Created by dongz on 2017/7/25.
 */
public class Main {
    private String standardExcelPath;
    private String targetExcelPath;
    private int targetEnglishVolumeNumber;

    public static void main(String[] args) {
        Main main=new Main();
        main.setStandardExcelPath("StandardPatternEnglish-200.xlsx");
        main.setTargetExcelPath("Jahai(Bur2005)-1773W.xlsx","B");
        main.startMainStream();
    }

    private void startMainStream(){
        ArrayList<HashSet<String>> standardList = ExcelReader.readExcel(standardExcelPath);
        ArrayList<String> targetList = ExcelReader.readExcel(targetExcelPath, targetEnglishVolumeNumber);
        ArrayList<ArrayList<String>> standardDataWeNeedList=ExcelReader.getStandardDataWeNeedList();
        ArrayList<String> targetDataWeNeedList=ExcelReader.getTargetDataWeNeedList();
        System.out.println("standardList.size(): "+standardList.size());
        System.out.println("targetList.size(): "+targetList.size());
        System.out.println("standardDataWeNeedList.size(): "+standardDataWeNeedList.size());
        System.out.println("targetDataWeNeedList.size(): "+targetDataWeNeedList.size());
        for (int i=0;i<standardList.size();i++) {
            for(int j=0;j<targetList.size();j++) {
                String[] currentUnsplitedArray=targetList.get(j).split(",");    //目标表中未拆分的英语词数组
                for(String currentSplitedString:currentUnsplitedArray){
                    if(currentSplitedString.contains("("))continue;
                    if(standardList.get(i).contains(currentSplitedString)){
                        standardDataWeNeedList.get(i).add(targetDataWeNeedList.get(j));
                        break;
                    }
                }
            }
        }
        for (ArrayList<String> s : standardDataWeNeedList) {
            for (String a : s) {
                System.out.print(a+"    |");
            }
            System.out.println('\n');
        }
    }

    public void setStandardExcelPath(String standardExcelPath){
        this.standardExcelPath=standardExcelPath;
    }

    public void setTargetExcelPath(String targetExcelPath,String volumeName){
        targetEnglishVolumeNumber = checkEnglishVolumeNameValidationReturnInteger(volumeName);
        this.targetExcelPath=targetExcelPath;
    }

    //需要用户指定目标表中英文在哪一列，该函数检查用户输入是否有效，只接受字符串A-Z
    private int checkEnglishVolumeNameValidationReturnInteger(String volumeName) {
        char c=0;
        try{
            if(volumeName.length()>1)throw new Exception("Invalid volume name.");
            else c = volumeName.charAt(0);
            if(c>90 || c<65) throw new Exception("Invalid volume name.");
        }catch(Exception e){
            e.printStackTrace();
            System.exit(1);
        }
        return c-65;
    }
}
