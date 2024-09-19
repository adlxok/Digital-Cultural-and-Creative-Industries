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
export function validUsername(str) {
  // 首先检查str是否为空或者只包含空格  
  if (!str || str.trim() === '') {  
    return false; // 如果为空或只有空格，则返回false  
  }  
  return true;
}
