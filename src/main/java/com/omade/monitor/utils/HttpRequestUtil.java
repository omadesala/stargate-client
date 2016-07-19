package com.omade.monitor.utils;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.apache.commons.codec.DecoderException;
import org.apache.commons.codec.net.URLCodec;
import org.apache.http.Header;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicHeader;
import org.apache.http.protocol.HTTP;
import org.apache.log4j.Logger;

import com.google.common.collect.Maps;

public class HttpRequestUtil {

	private static Logger logger = Logger.getLogger(HttpRequestUtil.class);

	private static final String APPLICATION_JSON = "application/json";
	private static final String CONTENT_TYPE_TEXT_JSON = "text/json";

	public static String doGet(String url, Map<String, String> headers) {

		HttpClient httpclient = HttpClients.createDefault();
		logger.info("http get url: " + url);
		HttpGet httpGet = new HttpGet(url);

		Iterator<String> iterator = headers.keySet().iterator();
		while (iterator.hasNext()) {
			String key = iterator.next();
			String value = headers.get(key);
			httpGet.addHeader(key, value);
		}

		StringBuffer sb = new StringBuffer();
		HttpResponse res = null;
		try {
			res = httpclient.execute(httpGet);
			HttpEntity entity = res.getEntity();

			if (entity != null) {
				InputStream instream = entity.getContent();
				BufferedReader bufferedReader = new BufferedReader(
						new InputStreamReader(instream));

				String line = bufferedReader.readLine();
				while (line != null) {
					sb.append(line);
					line = bufferedReader.readLine();
				}
				bufferedReader.close();
			} else {
				logger.warn("entity is null ! ");
			}

			Header[] allHeaders = res.getAllHeaders();
			for (Header header : allHeaders) {
				System.out.println("header: " + header.toString());
			}

		} catch (ClientProtocolException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}

		return sb.toString();
	}

	public static HttpResponse doGetWithResponse(String url,
			Map<String, String> headers) {

		HttpClient httpclient = HttpClients.createDefault();
		logger.info("http get url: " + url);
		HttpGet httpGet = new HttpGet(url);

		Iterator<String> iterator = headers.keySet().iterator();
		while (iterator.hasNext()) {
			String key = iterator.next();
			String value = headers.get(key);
			httpGet.addHeader(key, value);
		}

		StringBuffer sb = new StringBuffer();
		HttpResponse res = null;
		try {
			res = httpclient.execute(httpGet);
			HttpEntity entity = res.getEntity();

			if (entity != null) {
				InputStream instream = entity.getContent();
				BufferedReader bufferedReader = new BufferedReader(
						new InputStreamReader(instream));

				String line = bufferedReader.readLine();
				while (line != null) {
					sb.append(line);
					line = bufferedReader.readLine();
				}
				bufferedReader.close();
			} else {
				logger.warn("entity is null ! ");
			}

		} catch (ClientProtocolException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
			logger.error(e.getMessage());
		}
		return res;
	}

	public static Map<String, String> doHead(String url,
			Map<String, String> headers) {

		HttpClient httpclient = HttpClients.createDefault();
		logger.info("http url: " + url);
		HttpGet httpGet = new HttpGet(url);

		Iterator<String> iterator = headers.keySet().iterator();
		while (iterator.hasNext()) {
			String key = iterator.next();
			String value = headers.get(key);
			httpGet.addHeader(key, value);
		}

		Map<String, String> responseHeaders = Maps.newHashMap();
		try {
			HttpResponse res = httpclient.execute(httpGet);
			Header[] allHeaders = res.getAllHeaders();
			for (Header header : allHeaders) {
				responseHeaders.put(header.getName(), header.getValue());
			}

		} catch (ClientProtocolException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return responseHeaders;
	}

	public static String doPost(String url, Map<String, String> headers) {

		HttpClient httpclient = HttpClients.createDefault();
		logger.info("http post url: " + url);
		HttpPost httpPost = new HttpPost(url);
		Iterator<String> iterator = headers.keySet().iterator();
		while (iterator.hasNext()) {
			String key = iterator.next();
			String value = headers.get(key);
			// httpPost.addHeader(key,value);
			try {
				httpPost.addHeader(URLEncoder.encode(key, "UTF-8"),
						URLEncoder.encode(value, "UTF-8"));
			} catch (UnsupportedEncodingException e) {
				e.printStackTrace();
			}
		}
		httpPost.setHeader(HTTP.CONTENT_TYPE,
				"application/x-www-form-urlencoded;charset=UTF-8");

		StringBuffer sb = new StringBuffer();
		HttpResponse res = null;
		try {
			res = httpclient.execute(httpPost);
			HttpEntity entity = res.getEntity();

			if (entity != null) {
				InputStream instream = entity.getContent();
				BufferedReader bufferedReader = new BufferedReader(
						new InputStreamReader(instream));
				String line = bufferedReader.readLine();
				while (line != null) {
					sb.append(line);
					line = bufferedReader.readLine();
				}
				bufferedReader.close();
			} else {
				sb.append(res.getStatusLine().getStatusCode());
			}
		} catch (ClientProtocolException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return sb.toString();
	}

	public static String doPost(String url, Map<String, String> headers,
			List<NameValuePair> formparams) {

		HttpClient httpclient = HttpClients.createDefault();
		logger.info("http post url: " + url);
		HttpPost httpPost = new HttpPost(url.trim());

		if (headers != null) {
			Iterator<String> iterator = headers.keySet().iterator();
			while (iterator.hasNext()) {
				String key = iterator.next();
				String value = headers.get(key);
				try {
					httpPost.addHeader(URLEncoder.encode(key, "UTF-8"),
							URLEncoder.encode(value, "UTF-8"));
				} catch (UnsupportedEncodingException e) {
					e.printStackTrace();
				}
			}
		}

		try {
			UrlEncodedFormEntity form = new UrlEncodedFormEntity(
					(List<? extends org.apache.http.NameValuePair>) formparams);
			httpPost.setEntity(form);
		} catch (UnsupportedEncodingException e1) {
			e1.printStackTrace();
		}

		httpPost.setHeader(HTTP.CONTENT_TYPE,
				"application/x-www-form-urlencoded;charset=UTF-8");

		StringBuffer sb = new StringBuffer();
		HttpResponse res = null;
		try {
			res = httpclient.execute(httpPost);
			HttpEntity entity = res.getEntity();

			if (entity != null) {
				InputStream instream = entity.getContent();
				BufferedReader bufferedReader = new BufferedReader(
						new InputStreamReader(instream));
				String line = bufferedReader.readLine();
				while (line != null) {
					sb.append(line);
					line = bufferedReader.readLine();
				}
				bufferedReader.close();
				instream.close();
			} else {
				sb.append(res.getStatusLine().getStatusCode());
			}
		} catch (ClientProtocolException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return sb.toString();
	}

	public static String doPost(String url, Map<String, String> headers,
			String json) {

		HttpClient httpclient = HttpClients.createDefault();
		logger.info("http post url: " + url);
		HttpPost httpPost = new HttpPost(url.trim());

		if (headers != null) {
			Iterator<String> iterator = headers.keySet().iterator();
			while (iterator.hasNext()) {
				String key = iterator.next();
				String value = headers.get(key);
				try {
					httpPost.addHeader(URLEncoder.encode(key, "UTF-8"),
							URLEncoder.encode(value, "UTF-8"));
				} catch (UnsupportedEncodingException e) {
					e.printStackTrace();
				}
			}
		}

		// httpPost.setHeader(HTTP.CONTENT_TYPE,
		// "application/x-www-form-urlencoded;charset=UTF-8");

		StringBuffer sb = new StringBuffer();
		HttpResponse res = null;
		try {

			StringEntity se = new StringEntity(json);
			se.setContentType(CONTENT_TYPE_TEXT_JSON);
			se.setContentEncoding(new BasicHeader(HTTP.CONTENT_TYPE,
					APPLICATION_JSON));
			httpPost.setEntity(se);

			res = httpclient.execute(httpPost);
			HttpEntity entity = res.getEntity();

			if (entity != null) {
				InputStream instream = entity.getContent();
				BufferedReader bufferedReader = new BufferedReader(
						new InputStreamReader(instream));
				String line = bufferedReader.readLine();
				while (line != null) {
					sb.append(line);
					line = bufferedReader.readLine();
				}
				bufferedReader.close();
				instream.close();
			} else {
				sb.append(res.getStatusLine().getStatusCode());
			}
		} catch (ClientProtocolException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return sb.toString();
	}

	public static String unencodeURI(String str) {
		URLCodec codec = new URLCodec();
		try {
			return codec.decode(str);
		} catch (DecoderException ee) {
			logger.warn("Error trying to encode string for URI", ee);
			return str;
		}

	}
}
