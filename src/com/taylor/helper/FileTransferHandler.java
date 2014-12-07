package com.taylor.helper;

import it.sauronsoftware.ftp4j.FTPDataTransferListener;

/**
 * Created by tad on 12/6/2014.
 */
public class FileTransferHandler implements FTPDataTransferListener {
        public void started() {
            // Transfer started
        }

        public void transferred(int length) {
            // Yet other length bytes has been transferred since the last time this
            // method was called
        }

        public void completed() {
            // Transfer completed
        }

        public void aborted() {
            // Transfer aborted
        }

        public void failed() {
            // Transfer failed
        }

}
