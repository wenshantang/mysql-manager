package cn.aresoft.manager.plugin;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import com.puff.exception.ViewException;
import com.puff.web.view.View;

public class FlashView extends View {

	private InputStream in;

	public FlashView(InputStream in) {
		super();
		this.in = in;
	}

	@Override
	public void view() throws ViewException {
		BufferedInputStream bis = null;
		BufferedOutputStream bos = null;
		try {
			OutputStream output = response.getOutputStream();
			response.setContentType("application/x-shockwave-flash");
			bis = new BufferedInputStream(in);
			bos = new BufferedOutputStream(output);
			byte data[] = new byte[1024 * 16];
			int size = bis.read(data);
			while (size != -1) {
				bos.write(data, 0, size);
				size = bis.read(data);
			}
			bos.flush();
		} catch (Exception e) {
			throw new ViewException(e);
		} finally {
			if (bis != null) {
				try {
					bis.close();
				} catch (IOException e) {
				}
			}
			if (bos != null) {
				try {
					bos.close();
				} catch (IOException e) {
				}
			}
		}
	}

}
