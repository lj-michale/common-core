/**
 * Copyright (C) 2008 Happy Fish / YuQing
 * <p>
 * FastDFS Java Client may be copied only under the terms of the GNU Lesser
 * General Public License (LGPL).
 * Please visit the FastDFS Home Page http://www.csource.org/ for more detail.
 */

package org.csource.fastdfs;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.InetSocketAddress;
import java.net.Socket;

import common.core.app.log.Logger;
import common.core.app.log.LoggerFactory;

/**
 * Tracker Server Info
 *
 * @author Happy Fish / YuQing
 * @version Version 1.11
 */
public class TrackerServer {
	private final Logger logger = LoggerFactory.getLogger(TrackerServer.class);
	protected Socket sock;
	protected InetSocketAddress inetSockAddr;

	/**
	 * Constructor
	 *
	 * @param sock
	 *            Socket of server
	 * @param inetSockAddr
	 *            the server info
	 */
	public TrackerServer(Socket sock, InetSocketAddress inetSockAddr) {
		this.sock = sock;
		this.inetSockAddr = inetSockAddr;
	}

	/**
	 * get the connected socket
	 *
	 * @return the socket
	 */
	public Socket getSocket() throws IOException {
		logger.debug("getSocket inetSockAddr= {}", this.inetSockAddr);
		if (this.sock == null) {
			logger.debug("ClientGlobal.getSocket inetSockAddr= {}", this.inetSockAddr);
			this.sock = ClientGlobal.getSocket(this.inetSockAddr);
		}

		return this.sock;
	}

	/**
	 * get the server info
	 *
	 * @return the server info
	 */
	public InetSocketAddress getInetSocketAddress() {
		return this.inetSockAddr;
	}

	public OutputStream getOutputStream() throws IOException {
		return this.sock.getOutputStream();
	}

	public InputStream getInputStream() throws IOException {
		return this.sock.getInputStream();
	}

	public void close() throws IOException {
		if (this.sock != null) {
			try {
				this.logger.debug("TrackerServer.closeSocket inetSockAddr={}", this.inetSockAddr);
				ProtoCommon.closeSocket(this.sock);
			} finally {
				this.sock = null;
			}
		}
	}

	protected void finalize() throws Throwable {
		this.close();
	}
}
