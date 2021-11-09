import java.io.*;

public class test {
    public static void main(String[] args) throws IOException {
        try{
            // 파일 객체 생성
            File file = new File("C:\\Users\\서지희\\OneDrive - 강원대학교\\대학\\WorkSpace\\분산처리\\GraphData\\test\\src\\main\\java\\twitter_combined.txt");

            // 입력 스트림 생성
            FileReader fileReader = new FileReader(file);
            // 입력 버퍼 생성
            BufferedReader bufferedReader = new BufferedReader(fileReader);
            String line = "";

            // 다섯줄만 출력하기로 함
            int count = 0;
            while((line = bufferedReader.readLine()) != null && count <= 5){
                System.out.println(line);
                count++;
            }
            //readLine()은 끝에 개행문자를 읽지 않는다.
            bufferedReader.close();
        }
        catch (FileNotFoundException e){}
        catch (IOException e){
            System.out.println(e);
        }

    }
}
