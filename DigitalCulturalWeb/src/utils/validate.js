/**
 * Created by PanJiaChen on 16/11/18.
 */

/**
 * @param {string} path
 * @returns {Boolean}
 */
export function isExternal(path) {
  return /^(https?:|mailto:|tel:)/.test(path)
}

/**
 * @param {string} str
 * @returns {Boolean}
 */
export function validUsername(username) {
  // 用户名只能包含字母、数字、下划线，且长度为3-16个字符
  const regExp = /^[a-zA-Z0-9_]{3,16}$/;
  return regExp.test(username);
}
