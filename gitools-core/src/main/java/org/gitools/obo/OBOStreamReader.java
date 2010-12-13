/*
 *  Copyright 2010 Universitat Pompeu Fabra.
 * 
 *  Licensed under the Apache License, Version 2.0 (the "License");
 *  you may not use this file except in compliance with the License.
 *  You may obtain a copy of the License at
 * 
 *       http://www.apache.org/licenses/LICENSE-2.0
 * 
 *  Unless required by applicable law or agreed to in writing, software
 *  distributed under the License is distributed on an "AS IS" BASIS,
 *  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  See the License for the specific language governing permissions and
 *  limitations under the License.
 *  under the License.
 */

package org.gitools.obo;

import java.io.IOException;
import java.io.Reader;
import java.net.URL;
import java.util.LinkedList;
import java.util.Stack;
import java.util.regex.Pattern;

//FIXME On development
public class OBOStreamReader {

	private static final Pattern STANZA_NAME_PATTERN = Pattern.compile("^\\[.*\\]$");
	private static final Pattern LINE_COMMENT_PATTERN = Pattern.compile("^\\!.*$");

	private static final int UNKNOWN = -1;
	private static final int COMMENT = 1;
	private static final int DOCUMENT_START = 10;
	private static final int DOCUMENT_END = 19;
	private static final int HEADER_START = 20;
	private static final int HEADER_END = 29;
	private static final int STANZA_START = 30;
	private static final int STANZA_END = 39;
	private static final int TAG_START = 40;
	private static final int TAG_END = 49;

	/*private static final int DBXREF_START = 50;
	private static final int DBXREF_START = 51;
	private static final int DBXREF_END = 52;*/

	private OBOStream stream;
	private Stack<OBOStream> streamStack;

	private LinkedList<OBOEvent> tokens;

	private boolean headerStarted;
	private boolean headerEnded;

	private String stanzaName;
	private String tagName;

	public OBOStreamReader(Reader reader, URL baseUrl) {
		stream = new OBOStream(baseUrl);
		streamStack = new Stack<OBOStream>();

		tokens = new LinkedList<OBOEvent>();
		tokens.offer(new OBOEvent(DOCUMENT_START, 0));

		headerStarted = false;
		headerEnded = false;
		stanzaName = null;
	}

	public OBOEvent nextToken() throws IOException {
		if (tokens.size() > 0)
			return tokens.poll();

		while (tokens.size() == 0) {
			String line = stream.nextLine();
			int pos = stream.getLinePos();

			if (line == null) {
				tokens.offer(new OBOEvent(DOCUMENT_END, pos));
				break;
			}

			if (STANZA_NAME_PATTERN.matcher(line).matches()) {
				if (stanzaName == null) {
					if (!headerStarted) {
						tokens.offer(new OBOEvent(HEADER_START, pos));
						headerStarted = true;
					}
					if (!headerEnded) {
						tokens.offer(new OBOEvent(HEADER_END, pos));
						headerEnded = true;
					}
				}
				String stzName = line.substring(1, line.length() - 1);
				tokens.offer(new OBOEvent(STANZA_START, pos, stzName));
				stanzaName = stzName;
			}
			else if (LINE_COMMENT_PATTERN.matcher(line).matches()) {
				tokens.offer(new OBOEvent(COMMENT, pos));
			}
			else
				nextTag(line, pos);
		}

		return tokens.poll();
	}

	private void nextTag(String line, int linepos) {
		int pos = line.indexOf(':');
		if (pos < 0) {
			tokens.offer(new OBOEvent(UNKNOWN, linepos));
			return;
		}

		String name = line.substring(0, pos);
		String content = line.substring(pos + 1);
		StringBuilder sb = new StringBuilder();

		//TODO parse contents and generate events
		escapeCharsAndRemoveComments(content, sb);
		tokens.offer(new OBOEvent(TAG_START, linepos, stanzaName, name, sb.toString()));
		tokens.offer(new OBOEvent(TAG_END, linepos, stanzaName, name, sb.toString()));
	}

	/** replace escape characters and remove comments */
	private void escapeCharsAndRemoveComments(String line, StringBuilder sb) {
		int len = line.length();
		int pos = 0;
		while (pos < len) {
			char c = line.charAt(pos++);
			if (c != '\\' || (c == '\\' && pos == len - 1))
				sb.append(c);
			else if (c == '!')
				pos = len;
			else {
				c = line.charAt(pos++);
				switch (c) {
					case 'n': sb.append('\n'); break;
					case 'W': sb.append(' '); break;
					case 't': sb.append('\t'); break;
					case ':': sb.append(':'); break;
					case ',': sb.append(','); break;
					case '"': sb.append('"'); break;
					case '\\': sb.append('\\'); break;
					case '(': sb.append('('); break;
					case ')': sb.append(')'); break;
					case '[': sb.append('['); break;
					case ']': sb.append(']'); break;
					case '{': sb.append('{'); break;
					case '}': sb.append('}'); break;
					default: sb.append(c); break;
				}
			}
		}
	}
}
