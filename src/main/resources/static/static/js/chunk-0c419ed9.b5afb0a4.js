(window["webpackJsonp"]=window["webpackJsonp"]||[]).push([["chunk-0c419ed9"],{"0185":function(t,e,a){"use strict";a.r(e);var o=function(){var t=this,e=t.$createElement,a=t._self._c||e;return a("div",{staticClass:"app-container"},[a("div",{staticClass:"form"},[a("h4",[t._v("上传授权取消文件")]),t._v(" "),a("el-form",{ref:"editForm",attrs:{model:t.editForm,rules:t.editFormRules,"label-width":"140px"},nativeOn:{submit:function(t){t.preventDefault()}}},[a("el-form-item",{attrs:{label:"上传文件:"}},[a("el-upload",{ref:"upload",staticClass:"upload-demo",attrs:{action:"string","http-request":t.doUpload,"file-list":t.fileList,limit:1,name:"file","auto-upload":!1}},[a("el-button",{attrs:{slot:"trigger",icon:"el-icon-upload2",size:"mini",type:"primary"},slot:"trigger"},[t._v("上传授权取消")]),t._v(" "),a("el-button",{staticStyle:{"padding-left":"40px"},attrs:{type:"text",icon:"el-icon-download",size:"small"},on:{click:t.downloadWad}},[t._v("下载授权取消模板")]),t._v(" "),a("div",{staticClass:"el-upload__tip",attrs:{slot:"tip"},slot:"tip"},[t._v("只能上传1个xlsx/excel文件")])],1)],1),t._v(" "),a("el-form-item",{staticClass:"form-item",attrs:{label:"总笔数:",prop:"totalTrans"}},[a("el-input",{staticClass:"filter-item",staticStyle:{width:"40%"},attrs:{type:"number",placeholder:"总笔数应与上传文件一致"},model:{value:t.editForm.totalTrans,callback:function(e){t.$set(t.editForm,"totalTrans",e)},expression:"editForm.totalTrans"}})],1),t._v(" "),a("el-form-item",{staticClass:"form-item",attrs:{label:"密码:",prop:"password"}},[a("el-input",{staticClass:"filter-item",staticStyle:{width:"40%"},attrs:{type:"password","auto-complete":"new-password",placeholder:"请输入密码"},model:{value:t.editForm.password,callback:function(e){t.$set(t.editForm,"password",e)},expression:"editForm.password"}})],1),t._v(" "),a("el-form-item",{staticClass:"form-item"},[a("el-button",{attrs:{type:"success",size:"mini"},on:{click:function(e){return t.$refs.upload.submit()}}},[t._v("提交")])],1),t._v(" "),t.batchNo?a("el-form-item",{staticClass:"form-item"},[a("div",[a("span",{staticStyle:{color:"#33CC99"}},[t._v("上传成功，")]),t._v("批次号："+t._s(t.batchNo))])]):t._e()],1)],1)])},s=[],l=a("365c"),i={name:"uploading.vue",data:function(){return{editForm:{password:"",totalTrans:""},editFormRules:{password:[{required:!0,message:"密码不能为空",trigger:"blur"}],totalTrans:[{required:!0,message:"总比数不能为空",trigger:"blur"}]},fileList:[],batchNo:""}},created:function(){},methods:{doUpload:function(t){var e=this,a=new FormData;a.append("file",t.file),a.append("password",this.editForm.password),a.append("password",this.editForm.totalTrans),Object(l["b"])("wad/excelImport",a).then((function(t){200==t.code&&(e.batchNo=t.data.batchNo,e.$message.success({type:"success",message:"上传成功"}),e.fileList=[],e.editForm={password:"",totalTrans:""})})).catch((function(t){console.log("error",t),e.$message.error(t.message)}))},downloadWad:function(){Object(l["a"])("wad/download/wad").then((function(t){if(200==t.code){var e=document.createElement("a"),a=new Blob([t.data],{type:"application/vnd.ms-excel"});e.style.display="none",e.href=URL.createObjectURL(a),e.setAttribute("download","授权取消模板.xls"),document.body.appendChild(e),e.click(),document.body.removeChild(e)}})).catch((function(t){console.log(t);var e=document.createElement("a"),a=new Blob([t.data],{type:"application/vnd.ms-excel"});e.style.display="none",e.href=URL.createObjectURL(a),e.setAttribute("download","授权取消模板.xls"),document.body.appendChild(e),e.click(),document.body.removeChild(e)}))}}},r=i,n=(a("64a4"),a("2877")),d=Object(n["a"])(r,o,s,!1,null,"41e21e94",null);e["default"]=d.exports},"64a4":function(t,e,a){"use strict";a("b908")},b908:function(t,e,a){}}]);