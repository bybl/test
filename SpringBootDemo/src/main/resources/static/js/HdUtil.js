/**
 * 工具
 * @type {{}}
 */
function HdUtil() {

};
//常量
HdUtil.constant = {
    INFO: "提示",
    ERROR: "错误",
    WARINING: "警告",
    CONFIRM: "确认",
    BUILD_YES: "是否为上方所选故障创建维修计划?",
    SAVE_SUCCESS: "保存成功",
    SYNCHRO_CONFIRM: "确认同步吗",
    DELETE_CONFIRM: "确认删除吗",
    IGNORE_REMIND: "确定忽略该提醒吗？",
    DELAY_REMIND: "确定延迟该提醒吗？",
    SET_SUCCESS: "操作成功",
    BUILD_CONFIRM: "确认生成定检保养计划吗",
    BUILD_SUCCESS: "生成计划成功",
    DELETE_SUCCESS: "删除成功",
    CREATE_SUCCESS: "月度运行报表生成成功！",
    SELECT_EDIT_ROW: "请选择要编辑的行",
    SELECT_CARGOINOUT_ROW: "选中记录需为进库且包含一程船信息！",
    PAGE_SIZE: 30,
    PAGE_SIZE_TEN: 10

};
/**
 * easyui常用弹框
 * @type {{}}
 */
HdUtil.messager = {
    info: function (msg) {
        $.messager.alert(HdUtil.constant.INFO, msg, "info");
    },
    error: function (msg) {
        $.messager.alert(HdUtil.constant.ERROR, msg, "error");
    },
    warning: function (msg) {
        $.messager.alert(HdUtil.constant.WARINING, msg, "warning");
    },
    slide: function (msg) {
        $.messager.show({
            title: HdUtil.constant.INFO,
            msg: msg,
            timeout: 2000,
            showType: 'slide'
        });
    },
    slideReport: function (msg) {
        $.messager.show({
            title: HdUtil.constant.INFO,
            msg: msg,
            timeout: 5000,
            showType: 'slide'
        });
    }
};
HdUtil.formatter = {
    /**
     * 数字格式化
     * @param number 要格式化的数字
     * @param digit 保留几位小数
     * @returns {string}
     */
    numberFormat: function format(number, digit) {
        var n = parseFloat(number).toFixed(digit);
        var re = /(\d{1,3})(?=(\d{3})+(?:\.))/g;
        return n.replace(re, "$1,");
    }
};
HdUtil.date = {
    /**
     * 获取本月
     */
    getCurrentMonth: function () {
        return new Date().format("yyyy-MM");
    },
    getCurrentDate: function () {
        return new Date().format("yyyy-MM-dd");
    },
    getCurrentDateRepProblem: function () {
        return new Date().format("MM/dd/yyyy");
    },
    getDateThirtyDaysAgo: function () {
        var now = new Date();
        return new Date(now.getTime() - 30 * 24 * 60 * 60 * 1000).format("yyyy-MM-dd");
    },
    getDateFifteenDaysAgo: function () {
        var now = new Date();
        return new Date(now.getTime() - 15 * 24 * 60 * 60 * 1000).format("yyyy-MM-dd");
    },
    getDateSevenDaysAgo: function () {
        var now = new Date();
        return new Date(now.getTime() - 7 * 24 * 60 * 60 * 1000).format("yyyy-MM-dd");
    },
    getDateThirtyDaysLater: function () {
        var now = new Date();
        return new Date(now.getTime() + 30 * 24 * 60 * 60 * 1000).format("yyyy-MM-dd");
    },
    getDateSevenDaysLater: function () {
        var now = new Date();
        return new Date(now.getTime() + 7 * 24 * 60 * 60 * 1000).format("yyyy-MM-dd");
    },
    getCurrentDateTime: function () {
        return new Date().format("yyyy-MM-dd hh:mm:ss");
    },
    getDateThirtyDaysAgo2: function () {
        var now = new Date();
        return new Date(now.getTime() - 30 * 24 * 60 * 60 * 1000).format("yyyy-MM-dd hh:mm");
    },
    getCurrentDateTimeNoSecond: function () {
        return new Date().format("MM/dd/yyyy hh:mm:ss");
    },
    getCurrentDateTimeAfterOne: function () {
        return new Date(new Date().getTime() + 24 * 60 * 60 * 1000).format("yyyy-MM-dd hh:mm:ss");
    },
    /**
     * 获取本周第一天日期
     */
    getCurrentWeekBeginDate: function () {
        var now = new Date();
        return new Date(now - (now.getDay() - 1) * 86400000).format("yyyy-MM-dd");
    },
    /**
     * 获取本月第一天日期
     */
    getCurrentMonthBeginDate: function () {
        var now = new Date();
        now.setDate(1);
        return now.format("yyyy-MM-dd");
    },
    /**
     * 获取上月第一天日期
     */
    getLastMonthBeginDate: function () {
        var now = new Date();
        now.setDate(1);
        return new Date(now.setMonth(now.getMonth() - 1)).format("yyyy-MM-dd");
    },
    /**
     * 获取上月最后一天日期
     */
    getLastMonthEndDate: function () {
        var now = new Date();
        now.setDate(1);
        return new Date(now.setDate(now.getDate() - 1)).format("yyyy-MM-dd");
    },
    /**
     * 比较日期大小
     */
    compDate: function (beginDt, endDt) {
        var begDateDateList;  //起始时间的年月日
        var begDateTimeList = [0, 0, 0];  //起始日期的时分秒
        var endDateDateList;
        var endDateTimeList = [0, 0, 0];
        begDateDateList = beginDt.toString().substr(0, 10).split("-");
        if (beginDt.indexOf(":") != -1) {
            begDateTimeList = beginDt.substring(11).split(":");
            if (begDateTimeList.length == 2) {
                begDateTimeList[2] = 0;
            }
        }
        endDateDateList = endDt.substr(0, 10).split("-");
        if (endDt.indexOf(":") != -1) {
            endDateTimeList = endDt.substring(11).split(":");
            if (endDateTimeList.length == 2) {
                endDateTimeList[2] = 0;
            }
        }
        var begDte = new Date(begDateDateList[0], begDateDateList[1], begDateDateList[2], begDateTimeList[0], begDateTimeList[1], begDateTimeList[2]);
        var endDte = new Date(endDateDateList[0], endDateDateList[1], endDateDateList[2], endDateTimeList[0], endDateTimeList[1], endDateTimeList[2]);
        if ((begDte.getTime() - endDte.getTime()) > 0) {
            return false;
        } else {
            return true;
        }
    }
};
HdUtil.combobox = {

    //获取设备combobox下拉
    getDevs: function (params) {
        var ret = {
            url: '../../DevInspectRecord/getDevCardBox',
            method: 'get',
            valueField: 'id',
            required: true,
            textField: 'devSimpNam',
            limitToList: true
        }
        return $.extend(ret, params);
    },
    getYearMonth: function (params) {
        var ret = {
            url: '../../CMonth/getMonth',
            method: 'get',
            valueField: 'yearMonth',
            required: true,
            textField: 'yearMonth',
            limitToList: true
        }
        return $.extend(ret, params);
    },
    //获取分类combobox下拉
    getDevSortNam: function (params) {
        var ret = {
            url: '../../CDevPos/getDevPos',
            method: 'get',
            valueField: 'sortCod',
            textField: 'sortNam',
            limitToList: true,
            // onLoadSuccess: function (data) {
            //     if (data && data.length > 0) {
            //         $(this).combobox('select', data[0].sortCod);
            //     }
            // }
        }
        return $.extend(ret, params);
    },
    //获取类型名称下拉
    getFldChiList: function (params) {
        var ret = {
            method: 'get',
            valueField: 'fldEng',
            textField: 'fldChi',
            required: false,
            limitToList: true,
            // onLoadSuccess: function (data) {
            //     if (data && data.length > 0) {
            //         $(this).combobox('select', data[0].fldEng);
            //     }
            // }
        }
        return $.extend(ret, params);
    },
    //获取人员下拉（全部）
    getPeopleBox: function (params) {
        var ret = {
            method: 'get',
            valueField: 'id',
            textField: 'empNam',
            required: true,
            limitToList: true
        }
        return $.extend(ret, params);
    },
    //获取人员下拉（全部）
    getPeopleDeptBox: function (params) {
        var ret = {
            method: 'get',
            valueField: 'id',
            textField: 'empNam',
            required: true,
            limitToList: true,
            onLoadSuccess: function (data) {
                if (data && data.length > 0) {
                    $(this).combobox('select', data[0].id);
                }
            }
        }
        return $.extend(ret, params);
    },
    //获取定检级别下拉
    getCheckLevelBox: function (params) {
        var ret = {
            method: 'get',
            valueField: 'levelId',
            textField: 'levelNam',
            editable: false,
            required: true,
            limitToList: true,
            onLoadSuccess: function (data) {
                if (data && data.length > 0) {
                    $(this).combobox('select', data[0].levelId);
                }
            }
        }
        return $.extend(ret, params);
    },
    /**
     * 代码表查询
     * @param params
     */
    getCodListByfldEng: function (params) {
        var ret = {
            method: 'get',
            valueField: 'code',
            textField: 'name',
            editable: false,
            required: true,
            limitToList: true
        }
        return $.extend(ret, params);
    },
    /**
     * 设备类型查询
     * @param params
     */
    getDevKindListByfldEng: function (params) {
        var ret = {
            method: 'get',
            valueField: 'code',
            textField: 'name',
            editable: false,
            required: false,
            limitToList: true
        }
        return $.extend(ret, params);
    },
    /**
     * 选择司机下拉
     * @param params
     */
    getDriverId: function (params) {
        var ret = {
            method: 'get',
            valueField: 'id',
            textField: 'empNam',
            editable: false,
            required: true,
            limitToList: true,
            onLoadSuccess: function (data) {
                if (data && data.length > 0) {
                    $(this).combobox('select', data[0].id);
                }
            }
        }
        return $.extend(ret, params);
    },
    /**
     * （领油记录用）油品查询
     * @param params
     */
    getOilCods: function (params) {
        var ret = {
            method: 'get',
            valueField: 'oilCod',
            textField: 'oilNam',
            editable: false,
            required: true,
            limitToList: true
        }
        return $.extend(ret, params);
    },
    /**
     * 设备部位下拉
     * @param params
     */
    getCdevPos: function (params) {
        var ret = {
            method: 'get',
            valueField: 'posId',
            textField: 'posNam',
            editable: false,
            required: true,
            limitToList: true
            // onLoadSuccess: function (data) {
            //     if (data && data.length > 0) {
            //         $(this).combobox('select', data[0].posId);
            //     }
            // }
        }
        return $.extend(ret, params);
    },
    /**
     * 油品下拉
     * @param params
     */
    getCOil: function (params) {
        var ret = {
            method: 'get',
            valueField: 'oilCod',
            textField: 'oilNam',
            editable: false,
            limitToList: true
        }
        return $.extend(ret, params);
    },
    /**
     * （领油记录用）加油部位查询
     * @param params
     */
    getPositionCods: function (params) {
        var ret = {
            method: 'get',
            valueField: 'posId',
            textField: 'posNam',
            editable: false,
            required: true,
            limitToList: true
        }
        return $.extend(ret, params);
    }
}
;
/**
 * 下拉
 * @type {{zwcm: HdUtil.combogrid.zwcm, dwmc: HdUtil.combogrid.dwmc}}
 */
HdUtil.combogrid = {
    //获取故障项目combobox下拉
    getFaultItems: function (params) {
        var ret = {
            panelWidth: 120,
            idField: 'id',
            textField: 'devSimpNam',
            mode: 'remote',
            url: '../../DevInspectRecord/getDevCard',
            columns: [[
                // {field: 'devNo', title: '设备编号', width: 180},
                // {field: 'devNam', title: '设备类型', width: 100},
                {field: 'devSimpNam', title: '设备简称', width: 120}
            ]],
            required: true,
            onSelect: function (index, data) {
                $("#devNo").val(data.devNo);
                $("#devSimpNam").val(data.devSimpNam);
            },
            onLoadSuccess: function (data) {
                if (data && data.length > 0) {
                    $(this).combobox('select', data[0].id);
                }
            }
        };
        return $.extend(ret, params);
    },
    /**
     * 工作过程下拉
     * @param params
     */
    getWorkTypNam: function (params) {
        var ret = {
            panelWidth: 380,
            idField: 'fid',
            textField: 'procNam',
            mode: 'remote',
            // url: '../../WorkDetailRecord/getWorkTypNam',
            columns: [[
                // {field: 'devNo', title: '设备编号', width: 180},
                {field: 'classNam', title: '班次', width: 100},
                {field: 'workDte', title: '计划日期', width: 130},
                {field: 'procNam', title: '工作过程', width: 70},
                {field: 'workTypNam', title: '作业类型', width: 70}
            ]],
            required: true,
            onLoadSuccess: function (data) {
                if (data && data.length > 0) {
                    $(this).combobox('select', data[0].fid);
                }
            }
        };
        return $.extend(ret, params);
    },
    //设备选择下拉(combogrid)
    getDev: function (params) {
        var ret = {
            panelWidth: 170,
            idField: 'id',
            textField: 'devSimpNam',
            mode: 'remote',
            url: '../../DevInspectRecord/getDevCardBox',
            columns: [[
                {
                    field: 'id',
                    checkbox: true
                }, {
                    field: 'devSimpNam',
                    title: '全选',
                    width: 120
                }
            ]],
            required: true,
        };
        return $.extend(ret, params);
    },
    //分类名称选择下拉
    getDevSortNam: function (params) {
        var ret = {
            panelWidth: 410,
            idField: 'sortCod',
            textField: 'sortNam',
            mode: 'remote',
            url: '../../CDevPos/getDevPos',
            columns: [[
                {field: 'sortCod', title: '分类代码', width: 100},
                {field: 'sortNam', title: '分类名称', width: 120}
            ]],
            required: true,
            onSelect: function (index, data) {
                $("#sortCod").val(data.sortCod);
                $("#sortNam").val(data.sortNam);
            },
            onLoadSuccess: function (data) {
                if (data && data.length > 0) {
                    $(this).combobox('select', data[0].code);
                }
            }
        };
        return $.extend(ret, params);
    },
    //设备部位下拉 
    getCdevPos: function (params) {
        var ret = {
            panelWidth: 200,
            idField: 'posId',
            textField: 'posNam',
            mode: 'remote',
            url: '../../CDevPos/getCdevPos',
            columns: [[
                {field: 'posNam', title: '部位名称', width: 120}
            ]],
            required: true,
            onSelect: function (index, data) {
                $("#devPosId").val(data.posId);
                $("#devPosNam").val(data.posNam);
            },
            onLoadSuccess: function (data) {
                if (data && data.length > 0) {
                    $(this).combobox('select', data[0].code);
                }
            }
        };
        return $.extend(ret, params);
    }
};
HdUtil.combotree = {
    //部门选择下拉
    getDeptList: function (params) {
        var ret = {
            url: '../../DevInspectRecord/getDeptMenuByCod',
            checkbox: false,
            mode: 'remote',
            idField: 'id',
            panelHeight: 300,
            panelWidth: 200,
            textField: 'text',
            required: true,
            onLoadSuccess: function (data) {
                if (data && data.length > 0) {
                    $(this).combobox('select', data[0].id);
                }
            }
        };
        return $.extend(ret, params);
    }
};
Date.prototype.format = function (format) {
    var o = {
        "M+": this.getMonth() + 1, //month
        "d+": this.getDate(), //day
        "h+": this.getHours(), //hour
        "m+": this.getMinutes(), //minute
        "s+": this.getSeconds(), //second
        "q+": Math.floor((this.getMonth() + 3) / 3), //quarter
        "S": this.getMilliseconds() //millisecond
    }
    if (/(y+)/.test(format)) format = format.replace(RegExp.$1, (this.getFullYear() + "").substr(4 - RegExp.$1.length));
    for (var k in o) if (new RegExp("(" + k + ")").test(format))
        format = format.replace(RegExp.$1, RegExp.$1.length == 1 ? o[k] : ("00" + o[k]).substr(("" + o[k]).length));
    return format;
};
/**
 *
 * @requires jQuery
 *
 * 将form表单元素的值序列化成对象
 *
 * @returns object
 */
$.serializeObject = function (form) {
    var o = {};
    $.each(form.serializeArray(), function (index) {
        if (o[this['name']]) {
            o[this['name']] = o[this['name']] + "," + this['value'];
        } else {
            o[this['name']] = this['value'];
        }
    });
    return o;
};
$.ajaxSetup({
    cache: false,
    error: function (XMLHttpRequest, textStatus, errorThrown) {
        switch (XMLHttpRequest.status) {
            case(401):
                alert("没有权限！");
                break;
        }
    }
});