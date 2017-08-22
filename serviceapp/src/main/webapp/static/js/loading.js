/**
 * 加载显示
 */
function showLoadingToast() {
    var loading = document.getElementById("my_loading");
    loading.style.display = 'block';
}

/**
 * 加载关闭
 */
function hideLoadingToast() {
    var loading = document.getElementById("my_loading");
    loading.style.display = 'none';
}


/**
 * 设置加载中的文字
 */
function setLoadingToastText(text) {
    var loadingText = document.getElementById("my_loading_content");
    loadingText.innerText = text;
}