/*package common.core.web.interceptor;

import java.io.BufferedReader;
import java.io.IOException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;
import com.alibaba.fastjson.JSONObject;

public class MockInterceptor extends HandlerInterceptorAdapter {

	@Autowired
	private Environment env;

	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
			throws Exception {
		// 配置文件mock 开关
		String mockTag = env.getProperty("apis.mock.tag");
		// 请求路径mock 开关
		String httpPathTag = request.getParameter("mock");
		// 请求参数mock 开关 
		String paramTag = "";
		JSONObject jsonObject = new JSONObject();
		
		if(null != request.getReader()){
			jsonObject = (JSONObject) jsonObject.parse(ReadAsChars(request));
			if (null != jsonObject) {
				paramTag = jsonObject.getString("mock");
			}
		}
		
		
		if ("true".equals(mockTag)) {
			if ("/system-admin/app/view/listApiMethodInvokeObjectView".equals(request.getRequestURI())
					|| "/system-admin/app/view/getApiMethodInvokeObjectView".equals(request.getRequestURI())) {
				return super.preHandle(request, response, handler);
			}

			request.setAttribute("originalPath", request.getRequestURI());
			request.getRequestDispatcher("/system-admin/mock/result").forward(request,response);

			return false;
		} else if("true".equals(httpPathTag) || "true".equals(paramTag)) {
			request.setAttribute("originalPath", request.getRequestURI());
			request.getRequestDispatcher("/system-admin/mock/result").forward(request,response);
			return false;
		}else{
			return super.preHandle(request, response, handler);
		}
		return super.preHandle(request, response, handler);
	}
	
	public static String ReadAsChars(HttpServletRequest request) {

		BufferedReader br = null;
		StringBuilder sb = new StringBuilder("");
		try {
			br = request.getReader();
			String str;
			while ((str = br.readLine()) != null) {
				sb.append(str);
			}
			br.close();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			if (null != br) {
				try {
					br.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
		return sb.toString();
	}

}*/
