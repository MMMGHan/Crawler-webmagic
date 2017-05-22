package Util;

import java.io.*;

import org.apache.commons.io.FileUtils;
import org.apache.commons.io.IOUtils;
import us.codecraft.webmagic.Page;
import us.codecraft.webmagic.Request;
import us.codecraft.webmagic.selector.PlainText;

public class GetAjaxHtml {
    public static String getAjaxContent(String url) throws Exception {
        String command = "/Users/amoism/bin/phantomjs /Users/amoism/Projects/phantomjs/codes.js " + url;
        System.out.println("command = " + command);
        CommandRunner runner = new CommandRunner();
        runner.setCommandWorkDir("/Users/amoism/");
        CommandRunner.CommandResult result = runner.syncExecute(command);
        if (result.getExitCode()== CommandRunner.CommandCode.SUCCESS) {
            return result.getLog();
        } else {
            throw new RuntimeException(result.getErrorLog());
        }
    }

    public static void main(String[] args)  {
        try {
            String html = getAjaxContent("https://s.taobao.com/list?spm=a217f.8051907.312344.1.HFZDEO&q=%E8%BF%9E%E8%A1%A3%E8%A3%99&cat=16&style=grid&seller_type=taobao");
            System.out.println(html);
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    public static Page download(Request request) {
        Page page = new Page();
        try {
            String url = request.getUrl();
            String html = getAjaxContent(url);
            page.setRawText(html);
            page.setUrl(new PlainText(url));
            page.setRequest(request);
            return page;
        } catch (Exception e) {
            System.out.println("download出错了!");
            return page;
        }
    }

//    public static void main(String[] args) throws Exception {
//        long start = System.currentTimeMillis();
//        String result = getAjaxContent("https://s.taobao.com/list?spm=a217f.8051907.312344.1.HFZDEO&q=%E8%BF%9E%E8%A1%A3%E8%A3%99&cat=16&style=grid&seller_type=taobao");
//        System.out.println(result);
//        // 创建新文件
//        String path = "/Users/amoism/Documents/taobao.html";
//        PrintWriter printWriter = null;
//        printWriter = new PrintWriter(new FileWriter(new File(path)));
//        printWriter.write(result);
//        printWriter.close();
//        long end = System.currentTimeMillis();
//        System.out.println("===============耗时：" + (end - start)
//                + "===============");
//    }
}
