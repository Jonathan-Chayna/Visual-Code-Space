/**
 * Copyright (c) 2015-2017 Angelo ZERR. This program and the accompanying materials are made
 * available under the terms of the Eclipse Public License 2.0 which is available at
 * https://www.eclipse.org/legal/epl-2.0/
 *
 * <p>SPDX-License-Identifier: EPL-2.0
 *
 * <p>Initial code from https://github.com/microsoft/vscode-textmate/ Initial copyright Copyright
 * (C) Microsoft Corporation. All rights reserved. Initial license: MIT
 *
 * <p>Contributors: - Microsoft Corporation: Initial code, written in TypeScript, licensed under MIT
 * license - Angelo Zerr <angelo.zerr@gmail.com> - translation and adaptation to Java
 */
package org.eclipse.tm4e.core.grammar;

/**
 * Represents a "pushed" state on the stack (as a linked list element).
 *
 * @see <a href="https://github.com/microsoft/vscode-textmate/blob/main/src/main.ts">
 *     github.com/microsoft/vscode-textmate/blob/main/src/main.ts</a>
 */
public interface IStateStack {
  int getDepth();
}