/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.nio.file.Paths;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.Part;

/**
 *
 * @author user
 */
@MultipartConfig // <---- ใส่เพื่อบอก ว่าจะใช้ ความสามารถของ servlet 3.0 ในการดึงข้อมูลจากไฟล์ที่อัพโหลด
public class UploadServlet extends HttpServlet {

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        Part filePart = request.getPart("file"); // เอาข้อมูลมาจาก form ที่มี input ชื่อ file โดยเป็น input type = file

        String fileName = Paths.get(filePart.getSubmittedFileName()).getFileName().toString(); // เอาชื่อไฟล์ออกมา จะมีนามสกุลติดมาด้วย ถ้าจะเอา แค่นามสกุลแล้วใช้ชื่อไฟล์ที่เจน เอง ก็ให้ใช้ substring
        InputStream fileContent = filePart.getInputStream(); // เอาข้อมูลตัวไฟล์(ที่เป้น byte)ออกมาเก็บใน inputstream เพื่อรอการเขียนไฟล์
        OutputStream outputStream = null;
        try {
            outputStream // สร้าง outputStream ในการเขียนไฟล์ 
                    = new FileOutputStream(new File("C:\\Users\\user\\Documents\\NetBeansProjects\\templete\\UploadExample\\web\\image\\" + fileName));
            int read = 0;
            byte[] bytes = new byte[1024]; // สร้าง byte ในการทีจะบอกว่าจะให้เขียนไฟล์ ทีละ กี่ byte
            while ((read = fileContent.read(bytes)) != -1) { // เป็นการสั่งให้เขียนไฟล์ ทีละ 1024 byte ถ้าเขียนหมดแล้ว จะ return -1
                outputStream.write(bytes, 0, read); // เขียนไฟล์ ตามจำนวน byte ที่ read มาได้ โดยให้เริ่มตั้งแต่ byte ที่ 0
            }
            System.out.println("Done!");
        } catch (FileNotFoundException e) {
            System.out.println(e);
        } finally { // ปิด outputStream เมื่อ เขียนเสร็จเพื่อประหยัด resource
            if (outputStream != null) {
                try {
                    outputStream.close();
                } catch (IOException e) {
                    System.out.println(e);
                }
            }
        }
        getServletContext().getRequestDispatcher("/success.jsp").forward(request, response);
    }

    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
    /**
     * Handles the HTTP <code>GET</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Handles the HTTP <code>POST</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Returns a short description of the servlet.
     *
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

}
