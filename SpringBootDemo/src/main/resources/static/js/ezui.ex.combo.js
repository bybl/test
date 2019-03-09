/* 
 * ezui combo扩展
 */
$.extend($.fn.combobox.defaults, {
    /**
     * 重写combobox关闭面板事件。
     */
    onHidePanel: function () {
        var valueField = $(this).combobox("options").valueField;
        var val = $(this).combobox("getValue");  //当前combobox的值
        var allData = $(this).combobox("getData");   //获取combobox所有数据
        var result = true;      //为true说明输入的值在下拉框数据中不存在
        for (var i = 0; i < allData.length; i++) {
            if (val == allData[i][valueField]) {
                result = false;
            }
        }
        if (result) {
            $(this).combobox("clear");
        }
    }
});
