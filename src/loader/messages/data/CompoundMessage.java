/** 
 * This file is part of GenericPropertyLoader project.
 *
 * GenericPropertyLoader is free software: you can redistribute it and/or modify
 * it under the terms of the GNU Lesser General Public License as 
 * published by the Free Software Foundation, either version 3 of 
 * the License, or (at your option) any later version.
 *
 * GenericPropertyLoader is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * and GNU Lesser General Public License along with GenericPropertyLoader project.
 * If not, see <http://www.gnu.org/licenses/>.
 **/

package loader.messages.data;

import java.util.ArrayList;
import java.util.List;
import java.util.ListIterator;
import java.util.StringTokenizer;

import loader.messages.DMLoader;
import loader.messages.IDMLoaderMessages;

/**
 * A compound message is a message with parameters like
 * "Hello {name}, be welcome on {apllication}". It is made for quick parameters
 * injection at running time. This is the data type used internally by DMLoader. <br>
 * When attempting to get a message, remember that a string noted
 * "{0},{2},{1}: Do {0}" needs a three elements table {var0,var1,var2) and will
 * result in {{var0.toString()},{{var1.toString()},{{var2.toString()}: Do
 * {{var0.toString()}}.
 * 
 * Copyright 2010, Raphael Mechali <br>
 * Distributed under Lesser GNU General Public License (LGPL)
 */
public class CompoundMessage {

	/** Dynamic parameter begin character **/
	public static final String DYNAMIC_PARAMETER_BEGIN = "{";

	/** Dynamic parameter end character **/
	public static final String DYNAMIC_PARAMETER_END = "}";

	/** Empty message part **/
	private static final MessagePart EMPTY_MESSAGE_PART = new StaticMessagePart(
			"");

	/**
	 * Static parts of message in the order they should be displayed (static
	 * part and dynamic parts are store here)
	 **/
	private final List<MessagePart> messageParts;

	/** Number of parameters awaited **/
	private int awaitedParametersCount;

	/** Message patterns **/
	private String messagePattern;

	/**
	 * Constructor
	 */
	public CompoundMessage(String messagePatter) {
		messageParts = new ArrayList<MessagePart>();
		setMessagePattern(messagePatter);
		// no need to add international messages as they are packaged with the
		// DMLoader one.
	}

	/**
	 * Sets the message pattern
	 * 
	 * @param messagePattern
	 *            : message pattern
	 * @throws IllegalArgumentException
	 *             if the message pattern is null or bad formatted
	 */
	private void setMessagePattern(String messagePattern) {
		// precondition : the message is not void
		if (messagePattern == null) {
			// throw an uncaught exception
			throw new RuntimeException("Cannot set a null message pattern");
		}

		this.messagePattern = messagePattern;

		// clear previous value
		messageParts.clear();

		// parse the message to find {N} instances
		StringTokenizer tokenizer = new StringTokenizer(messagePattern,
				DYNAMIC_PARAMETER_BEGIN);

		int numberOfParametersAwaited = 0;
		if (tokenizer.countTokens() == 0) {
			// no message, just let this message worth ""
			messageParts.add(EMPTY_MESSAGE_PART);
		} else {
			// the message is made at list of one static part, build for every
			// token the corresponding parts
			// A - first message part
			String currentString = tokenizer.nextToken();
			messageParts.add(new StaticMessagePart(currentString));
			// B - every following message parts
			while (tokenizer.hasMoreTokens()) {
				currentString = tokenizer.nextToken();

				// the string should be compound as
				// [Number][DYNAMIC_PARAMETER_END][Next message]
				int dynamicParamEndIndex = currentString
						.indexOf(DYNAMIC_PARAMETER_END);
				if (dynamicParamEndIndex == -1) {
					throw new IllegalArgumentException(DMLoader.getMessage(
							IDMLoaderMessages.BAD_FORMATTED_PARAMETER,
							messagePattern, DYNAMIC_PARAMETER_END,
							DYNAMIC_PARAMETER_BEGIN));
				}
				String dynamicParameterIndex = currentString.substring(0,
						dynamicParamEndIndex);

				try {
					Integer indexValue = new Integer(dynamicParameterIndex);
					if (indexValue < 0) {
						throw new IllegalArgumentException(DMLoader.getMessage(
								IDMLoaderMessages.INVALID_PARAMETER_INDEX,
								messagePattern, DYNAMIC_PARAMETER_END,
								DYNAMIC_PARAMETER_BEGIN));
					}
					messageParts.add(new DynamicMessagePart(indexValue));
					// store the number of parameters awaited (the higher index
					// conditions the parameters size)
					int awaitedSize = indexValue + 1;
					if (awaitedSize > numberOfParametersAwaited) {
						numberOfParametersAwaited = awaitedSize;
					}
				} catch (NumberFormatException n) {
					// the dynamic value is invalid
					throw new IllegalArgumentException(DMLoader.getMessage(
							IDMLoaderMessages.INVALID_PARAMETER_INDEX,
							messagePattern, DYNAMIC_PARAMETER_END,
							DYNAMIC_PARAMETER_BEGIN));
				}

				// find the optional constant message following the dynamic
				// value
				int stringLength = currentString.length();
				if (dynamicParamEndIndex + 1 < stringLength) {
					// the delimiter in not the last character...
					String constantMessagePart = currentString.substring(
							dynamicParamEndIndex + 1, stringLength);
					messageParts
							.add(new StaticMessagePart(constantMessagePart));
				}
			}

			// store the number of parameters awaited
			this.awaitedParametersCount = numberOfParametersAwaited;
		}
	}

	/**
	 * Getter -
	 * 
	 * @return the messagePattern
	 */
	public String getMessagePattern() {
		return messagePattern;
	}

	/**
	 * Getter -
	 * 
	 * @return the awaitedParametersCount
	 */
	public int getAwaitedParametersCount() {
		return awaitedParametersCount;
	}

	/**
	 * Formats the compound message
	 * 
	 * @return - the formatted string value
	 * @throws IllegalArgumentException
	 *             if the number of parameters is invalid
	 */
	public String formatMessage(Object[] parameters) {
		if (parameters.length != awaitedParametersCount) {
			// Invalid parameters number
			throw new IllegalArgumentException(DMLoader.getMessage(
					IDMLoaderMessages.INVALID_PARAMETERS_COUNT, messagePattern,
					awaitedParametersCount));
		}
		StringBuffer buffer = new StringBuffer();

		for (ListIterator<MessagePart> partIt = messageParts.listIterator(); partIt
				.hasNext();) {
			buffer.append(partIt.next().stringValue(parameters));
		}

		return buffer.toString();
	}

	/**
	 * A message part that can export itself as string
	 * 
	 */
	private interface MessagePart {

		/**
		 * Export this message part for parameters values
		 * 
		 * @param parametersValue
		 *            : parameters values
		 * @return - null if parameters invalid, the message part string
		 *         representation otherwise
		 */
		String stringValue(Object[] parametersValue);

	}

	/**
	 * A static message part
	 */
	static class StaticMessagePart implements MessagePart {

		/** Static message **/
		private final String staticMessage;

		/**
		 * Constructor
		 */
		public StaticMessagePart(String staticMessage) {
			this.staticMessage = staticMessage;
		}

		/**
		 * {@inherit}
		 */
		@Override
		public String stringValue(Object[] parametersValue) {
			return staticMessage;
		}
	}

	/**
	 * A dynamic message part
	 */
	static class DynamicMessagePart implements MessagePart {

		/** Dynamic index awaited **/
		private final int dynamicIndex;

		/**
		 * Constructor
		 */
		public DynamicMessagePart(int dynamicIndex) {
			this.dynamicIndex = dynamicIndex;
		}

		/**
		 * {@inherit}
		 */
		@Override
		public String stringValue(Object[] parametersValue) {
			// precondition : the table contains it
			return parametersValue[dynamicIndex].toString();
		}

	}

}
