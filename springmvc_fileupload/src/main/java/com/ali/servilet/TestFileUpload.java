package com.ali.servilet;
import com.ali.exception.SysException;
import com.sun.jersey.api.client.Client;
import com.sun.jersey.api.client.WebResource;
import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.UUID;

@Controller
@RequestMapping("user")
public class TestFileUpload {
	
	/**
     * 传统上传文件
     * @param request
     * @param upload
     * @return
     * @throws Exception
     */
    @RequestMapping("testFileupload")
    public String Method1(HttpServletRequest request) throws Exception {
        //获取文件路径
        String path = request.getSession().getServletContext().getRealPath("/upload/");

        File file = new File(path);
        //判断文件夹是否存在
        if (!file.exists()) {
            //不存在 则创建文件夹
            file.mkdirs();
        }
        //解析request对象 获取文件上传项目
        DiskFileItemFactory factory = new DiskFileItemFactory();
        ServletFileUpload upload = new ServletFileUpload(factory);
        //解析request
        List<FileItem> items = upload.parseRequest(request);
        for (FileItem item : items) {
            //判断当前文件是否是 上传的文件项目
            if (item.isFormField()){
                //不是文件项

            }else {
                //是上传的文件项
                String filename = item.getName();
                //生成唯一标识
                String replace = UUID.randomUUID().toString().replace("-", "");
                filename =replace+"_"+filename;
                item.write(new File(path,filename));


                //删除临时文件 大于10kb为临时文件 直接删除  小于10kb的直接存入session
                item.delete();
            }
        }

        System.out.println("文件上传...");
        return "success";
    }


//--------------------------------------------------------------------------------------------

    /**
     * springMVC上传文件
     * @param request
     * @param upload
     * @return
     * @throws Exception
     */
    @RequestMapping("testFileupload2")
    public String Method3(HttpServletRequest request,MultipartFile upload) throws Exception {
        //获取文件路径
        String path = request.getSession().getServletContext().getRealPath("/uploads/");

        File file = new File(path);
        //判断文件夹是否存在
        if (!file.exists()) {
            //不存在 则创建文件夹
            file.mkdirs();
        }

        //是上传的文件项
        String filename = upload.getOriginalFilename();

        //生成唯一标识
        String replace = UUID.randomUUID().toString().replace("-", "");
        filename =replace+"_"+filename;

        //文件上传
        upload.transferTo(new File(path,filename));

        System.out.println("文件上传...");
        return "success";
    }

//-------------------------------------------------------------------------------------------

    /**
     * 跨服务器上传文件
     * @param upload
     * @return
     * @throws Exception
     */
    @RequestMapping("testFileupload3")
    public String Method5(MultipartFile upload) throws IOException {
        //获取连接地址
        String path = "http://localhost:9090/updatas/";
        //获取初始文件路径
        String filename = upload.getOriginalFilename();
        //随机的文件名 保证唯一
        String replace = UUID.randomUUID().toString().replace("-", "");
        filename=replace+"_"+filename;

        //获取图片服务器连接
        Client client = Client.create();
        WebResource webResource = client.resource(path+filename);

        //上传文件
        webResource.put(upload.getBytes());
        return "success";

    }  
}
