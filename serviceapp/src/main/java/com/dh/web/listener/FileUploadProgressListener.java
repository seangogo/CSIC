package com.dh.web.listener;
import javax.servlet.http.HttpSession;

import org.apache.commons.fileupload.ProgressListener;
import org.springframework.stereotype.Component;

import com.dh.common.Progress;
/**
 * Created by Coolkid on 2016/10/8.
 */
@Component
public class FileUploadProgressListener implements ProgressListener {
        private HttpSession session;

        public void setSession(HttpSession session){
            this.session = session;
            Progress status = new Progress();
            session.setAttribute("status", status);
        }
        @Override
        public void update(long bytesRead, long contentLength, int items) {
            Progress status = (Progress) session.getAttribute("status");
            status.setBytesRead(bytesRead);
            status.setContentLength(contentLength);
            status.setItems(items);
        }
}
