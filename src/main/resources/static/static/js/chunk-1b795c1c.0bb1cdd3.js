(window["webpackJsonp"]=window["webpackJsonp"]||[]).push([["chunk-1b795c1c"],{"11e9":function(e,t,a){var i=a("52a7"),r=a("4630"),o=a("6821"),s=a("6a99"),l=a("69a8"),n=a("c69a"),c=Object.getOwnPropertyDescriptor;t.f=a("9e1e")?c:function(e,t){if(e=o(e),t=s(t,!0),n)try{return c(e,t)}catch(a){}if(l(e,t))return r(!i.f.call(e,t),e[t])}},"1a32":function(e,t,a){},"333d":function(e,t,a){"use strict";var i=function(){var e=this,t=e.$createElement,a=e._self._c||t;return a("div",{staticClass:"pagination-container",class:{hidden:e.hidden}},[a("el-pagination",e._b({attrs:{background:e.background,"current-page":e.currentPage,"page-size":e.pageSize,layout:e.layout,"page-sizes":e.pageSizes,total:e.total},on:{"update:currentPage":function(t){e.currentPage=t},"update:current-page":function(t){e.currentPage=t},"update:pageSize":function(t){e.pageSize=t},"update:page-size":function(t){e.pageSize=t},"size-change":e.handleSizeChange,"current-change":e.handleCurrentChange}},"el-pagination",e.$attrs,!1))],1)},r=[];a("c5f6");Math.easeInOutQuad=function(e,t,a,i){return e/=i/2,e<1?a/2*e*e+t:(e--,-a/2*(e*(e-2)-1)+t)};var o=function(){return window.requestAnimationFrame||window.webkitRequestAnimationFrame||window.mozRequestAnimationFrame||function(e){window.setTimeout(e,1e3/60)}}();function s(e){document.documentElement.scrollTop=e,document.body.parentNode.scrollTop=e,document.body.scrollTop=e}function l(){return document.documentElement.scrollTop||document.body.parentNode.scrollTop||document.body.scrollTop}function n(e,t,a){var i=l(),r=e-i,n=20,c=0;t="undefined"===typeof t?500:t;var u=function e(){c+=n;var l=Math.easeInOutQuad(c,i,r,t);s(l),c<t?o(e):a&&"function"===typeof a&&a()};u()}var c={name:"Pagination",props:{total:{required:!0,type:Number},page:{type:Number,default:1},limit:{type:Number,default:20},pageSizes:{type:Array,default:function(){return[10,20,30,50]}},layout:{type:String,default:"total, sizes, prev, pager, next, jumper"},background:{type:Boolean,default:!0},autoScroll:{type:Boolean,default:!0},hidden:{type:Boolean,default:!1}},computed:{currentPage:{get:function(){return this.page},set:function(e){this.$emit("update:page",e)}},pageSize:{get:function(){return this.limit},set:function(e){this.$emit("update:limit",e)}}},methods:{handleSizeChange:function(e){this.$emit("pagination",{page:this.currentPage,limit:e}),this.autoScroll&&n(0,800)},handleCurrentChange:function(e){this.$emit("pagination",{page:e,limit:this.pageSize}),this.autoScroll&&n(0,800)}}},u=c,d=(a("e498"),a("2877")),p=Object(d["a"])(u,i,r,!1,null,"6af373ef",null);t["a"]=p.exports},"5dbc":function(e,t,a){var i=a("d3f4"),r=a("8b97").set;e.exports=function(e,t,a){var o,s=t.constructor;return s!==a&&"function"==typeof s&&(o=s.prototype)!==a.prototype&&i(o)&&r&&r(e,o),e}},7456:function(e,t,a){},"8b97":function(e,t,a){var i=a("d3f4"),r=a("cb7c"),o=function(e,t){if(r(e),!i(t)&&null!==t)throw TypeError(t+": can't set as prototype!")};e.exports={set:Object.setPrototypeOf||("__proto__"in{}?function(e,t,i){try{i=a("9b43")(Function.call,a("11e9").f(Object.prototype,"__proto__").set,2),i(e,[]),t=!(e instanceof Array)}catch(r){t=!0}return function(e,a){return o(e,a),t?e.__proto__=a:i(e,a),e}}({},!1):void 0),check:o}},9093:function(e,t,a){var i=a("ce10"),r=a("e11e").concat("length","prototype");t.f=Object.getOwnPropertyNames||function(e){return i(e,r)}},aa77:function(e,t,a){var i=a("5ca1"),r=a("be13"),o=a("79e5"),s=a("fdef"),l="["+s+"]",n="​",c=RegExp("^"+l+l+"*"),u=RegExp(l+l+"*$"),d=function(e,t,a){var r={},l=o((function(){return!!s[e]()||n[e]()!=n})),c=r[e]=l?t(p):s[e];a&&(r[a]=c),i(i.P+i.F*l,"String",r)},p=d.trim=function(e,t){return e=String(r(e)),1&t&&(e=e.replace(c,"")),2&t&&(e=e.replace(u,"")),e};e.exports=d},aa98:function(e,t,a){"use strict";a("1a32")},b979:function(e,t,a){"use strict";a.r(t);var i=function(){var e=this,t=e.$createElement,a=e._self._c||t;return a("div",{staticClass:"app-container"},[a("el-col",{staticClass:"toolbar",staticStyle:{"padding-bottom":"0px"},attrs:{span:24}},[a("el-form",{attrs:{inline:!0,model:e.listQuery},nativeOn:{submit:function(e){e.preventDefault()}}},[a("el-form-item",{attrs:{label:"用户名:"}},[a("el-input",{staticClass:"filter-item",attrs:{placeholder:"请输入用户名"},model:{value:e.listQuery.keyword,callback:function(t){e.$set(e.listQuery,"keyword",t)},expression:"listQuery.keyword"}})],1),e._v(" "),a("el-form-item",[a("el-button",{attrs:{type:"success",size:"mini",icon:"el-icon-search"},on:{click:e.getList}},[e._v("查询")]),e._v(" "),a("el-button",{attrs:{type:"primary",size:"mini",icon:"el-icon-plus"},on:{click:e.handleAdd}},[e._v("添加")])],1)],1)],1),e._v(" "),a("el-table",{directives:[{name:"loading",rawName:"v-loading",value:e.loading,expression:"loading"}],ref:"table",staticStyle:{width:"100%"},attrs:{data:e.list,border:""}},[a("el-table-column",{attrs:{align:"center",prop:"username",label:"用户名"}}),e._v(" "),a("el-table-column",{attrs:{align:"center",prop:"nickName",label:"昵称"}}),e._v(" "),a("el-table-column",{attrs:{align:"center",prop:"email",label:"邮箱"}}),e._v(" "),a("el-table-column",{attrs:{align:"center",prop:"userType",label:"用户类型"},scopedSlots:e._u([{key:"default",fn:function(t){return[e._v("\n        "+e._s({0:"系统管理员",1:"业务用户",2:"日志员",3:"审核员"}[t.row.userType])+"\n      ")]}}])}),e._v(" "),a("el-table-column",{attrs:{align:"center",prop:"status",label:"帐号启用状态"},scopedSlots:e._u([{key:"default",fn:function(t){return[a("el-tag",{attrs:{type:1==t.row.status?"success":"danger"}},[e._v("\n          "+e._s({0:"禁用",1:"启用"}[t.row.status])+"\n        ")])]}}])}),e._v(" "),a("el-table-column",{attrs:{align:"center",prop:"icon",label:"头像"},scopedSlots:e._u([{key:"default",fn:function(t){return[t.row.icon?a("el-image",{staticClass:"td-img",attrs:{src:t.row.icon,"preview-src-list":e.srcList},on:{click:function(a){return e.vbs(t.row.icon)}}}):e._e()]}}])}),e._v(" "),a("el-table-column",{attrs:{align:"center",prop:"loginTime",label:"最后登录时间"}}),e._v(" "),a("el-table-column",{attrs:{align:"center",prop:"lastLoginIp",label:"最近一次登录IP地址",width:"150"}}),e._v(" "),a("el-table-column",{attrs:{align:"center",prop:"createTime",label:"添加时间"}}),e._v(" "),a("el-table-column",{attrs:{align:"center",fixed:"right",label:"操作",width:"140"},scopedSlots:e._u([{key:"default",fn:function(t){return[a("el-button",{staticClass:"btn",attrs:{type:"text",size:"small"},on:{click:function(a){return e.handleEdit(t.$index,t.row)}}},[e._v("编辑")]),e._v(" "),a("el-popover",{ref:"popover-"+t.row.ID,attrs:{placement:"top",width:"200",trigger:"manual"}},[a("p",[e._v("确定删除该数据吗？")]),e._v(" "),a("div",{staticStyle:{"text-align":"right",margin:"0"}},[a("el-button",{attrs:{size:"mini",type:"text"},on:{click:function(a){e.$refs["popover-"+t.row.ID].doClose()}}},[e._v("取消")]),e._v(" "),a("el-button",{attrs:{type:"primary",size:"mini"},on:{click:function(a){return e.handleDel(t.$index,t.row)}}},[e._v("确定")])],1),e._v(" "),a("el-button",{staticClass:"btn",attrs:{slot:"reference",type:"text",size:"small"},on:{click:function(a){e.$refs["popover-"+t.row.ID].doShow()}},slot:"reference"},[e._v("删除")])],1),e._v(" "),a("el-button",{staticClass:"btn",attrs:{type:"text",size:"small"},on:{click:function(a){return e.allocationEdit(t.$index,t.row)}}},[e._v("分配角色")]),e._v(" "),a("el-button",{staticClass:"btn",attrs:{type:"text",size:"small"},on:{click:function(a){return e.pwddit(t.$index,t.row)}}},[e._v("重置密码")])]}}])})],1),e._v(" "),a("pagination",{directives:[{name:"show",rawName:"v-show",value:e.total>0,expression:"total>0"}],attrs:{total:e.total,page:e.listQuery.pageNum,limit:e.listQuery.pageSize},on:{"update:page":function(t){return e.$set(e.listQuery,"pageNum",t)},"update:limit":function(t){return e.$set(e.listQuery,"pageSize",t)},pagination:e.getList}}),e._v(" "),a("el-dialog",{attrs:{title:e.textMap[e.dialogStatus],visible:e.dialogFormVisible,"close-on-click-modal":!1},on:{"update:visible":function(t){e.dialogFormVisible=t}}},[a("el-form",{ref:"editForm",attrs:{model:e.editForm,"label-width":"120px",rules:e.editFormRules}},[a("el-row",[a("el-col",{attrs:{span:12}},[a("el-form-item",{staticClass:"form-item",attrs:{label:"用户名",prop:"username"}},[a("el-input",{staticClass:"filter-item",attrs:{placeholder:"用户名"},model:{value:e.editForm.username,callback:function(t){e.$set(e.editForm,"username",t)},expression:"editForm.username"}})],1),e._v(" "),a("el-form-item",{staticClass:"form-item",attrs:{label:"昵称",prop:"nickName"}},[a("el-input",{staticClass:"filter-item",attrs:{placeholder:"昵称"},model:{value:e.editForm.nickName,callback:function(t){e.$set(e.editForm,"nickName",t)},expression:"editForm.nickName"}})],1),e._v(" "),a("el-form-item",{staticClass:"form-item",attrs:{label:"邮箱",prop:"email"}},[a("el-input",{staticClass:"filter-item",attrs:{placeholder:"邮箱"},model:{value:e.editForm.email,callback:function(t){e.$set(e.editForm,"email",t)},expression:"editForm.email"}})],1),e._v(" "),a("el-form-item",{staticClass:"form-item",attrs:{label:"用户类型",prop:"userType"}},[a("el-select",{staticClass:"form-select",staticStyle:{width:"100%"},attrs:{placeholder:"请选择",clearable:""},model:{value:e.editForm.userType,callback:function(t){e.$set(e.editForm,"userType",t)},expression:"editForm.userType"}},[a("el-option",{attrs:{value:0,label:"系统管理员"}}),e._v(" "),a("el-option",{attrs:{value:1,label:"业务用户"}}),e._v(" "),a("el-option",{attrs:{value:2,label:"日志员"}}),e._v(" "),a("el-option",{attrs:{value:3,label:"审核员"}})],1)],1)],1),e._v(" "),a("el-col",{attrs:{span:12}},[a("el-form-item",{staticClass:"form-item",attrs:{label:"头像图片",prop:"icon"}},[a("el-upload",{staticClass:"avatar-uploader",attrs:{action:"string","show-file-list":!1,"before-upload":e.handleImagesUrlBefore,"http-request":e.doUpload,"on-success":e.uploadUrl,accept:".jpg,.jpeg,.png,.gif"}},[e.editForm.icon?a("img",{staticClass:"avatar",attrs:{src:e.editForm.icon}}):a("i",{staticClass:"el-icon-plus avatar-uploader-icon"})])],1),e._v(" "),"update"==e.dialogStatus?a("el-form-item",{staticClass:"form-item",attrs:{label:"帐号启用状态",prop:"status"}},[a("el-radio-group",{model:{value:e.editForm.status,callback:function(t){e.$set(e.editForm,"status",t)},expression:"editForm.status"}},[a("el-radio",{attrs:{label:0}},[e._v("禁用")]),e._v(" "),a("el-radio",{attrs:{label:1}},[e._v("启用")])],1)],1):e._e(),e._v(" "),a("el-form-item",{staticClass:"form-item",attrs:{label:"备注信息",prop:"note"}},[a("el-input",{staticClass:"filter-item",attrs:{placeholder:"备注信息"},model:{value:e.editForm.note,callback:function(t){e.$set(e.editForm,"note",t)},expression:"editForm.note"}})],1)],1)],1)],1),e._v(" "),a("div",{staticClass:"dialog-footer",attrs:{slot:"footer"},slot:"footer"},[a("el-button",{attrs:{size:"mini"},nativeOn:{click:function(t){e.dialogFormVisible=!1}}},[e._v("取消")]),e._v(" "),a("el-button",{attrs:{type:"primary",size:"mini"},on:{click:e.createData}},[e._v(e._s("create"==e.dialogStatus?"添加":"编辑"))])],1)],1),e._v(" "),a("el-dialog",{attrs:{title:"分配角色",visible:e.dialogFRoleVisible,"close-on-click-modal":!1,width:"35%"},on:{"update:visible":function(t){e.dialogFRoleVisible=t}}},[a("el-form",{ref:"roleForm",attrs:{model:e.roleForm,"label-width":"120px",rules:e.roleFormRules}},[a("el-form-item",{staticClass:"form-item",attrs:{label:"角色",prop:"roleId"}},[a("el-select",{staticClass:"form-select",staticStyle:{width:"100%"},attrs:{placeholder:"请选择",clearable:""},model:{value:e.roleForm.roleId,callback:function(t){e.$set(e.roleForm,"roleId",t)},expression:"roleForm.roleId"}},e._l(e.roleList,(function(e){return a("el-option",{key:e.id,attrs:{value:e.id,label:e.name}})})),1)],1)],1),e._v(" "),a("div",{staticClass:"dialog-footer",attrs:{slot:"footer"},slot:"footer"},[a("el-button",{attrs:{size:"mini"},nativeOn:{click:function(t){e.dialogFRoleVisible=!1}}},[e._v("取消")]),e._v(" "),a("el-button",{attrs:{type:"primary",size:"mini"},on:{click:e.createRoleData}},[e._v("提交")])],1)],1),e._v(" "),a("el-dialog",{attrs:{title:"重置密码",visible:e.dialogPwdVisible,"close-on-click-modal":!1,width:"35%"},on:{"update:visible":function(t){e.dialogPwdVisible=t}}},[a("el-form",{ref:"pwdForm",attrs:{model:e.pwdForm,"label-width":"120px",rules:e.pwdFormRules}},[a("el-form-item",{staticClass:"form-item",attrs:{label:"登录密码",prop:"password"}},[a("el-input",{staticClass:"filter-item",attrs:{type:"password","auto-complete":"new-password",placeholder:"登录密码"},model:{value:e.pwdForm.password,callback:function(t){e.$set(e.pwdForm,"password",t)},expression:"pwdForm.password"}})],1),e._v(" "),a("el-form-item",{staticClass:"form-item",attrs:{label:"二次确认密码",prop:"password2"}},[a("el-input",{staticClass:"filter-item",attrs:{type:"password","auto-complete":"new-password",placeholder:"要与登录密码保持一致"},model:{value:e.pwdForm.password2,callback:function(t){e.$set(e.pwdForm,"password2",t)},expression:"pwdForm.password2"}})],1)],1),e._v(" "),a("div",{staticClass:"dialog-footer",attrs:{slot:"footer"},slot:"footer"},[a("el-button",{attrs:{size:"mini"},nativeOn:{click:function(t){e.dialogPwdVisible=!1}}},[e._v("取消")]),e._v(" "),a("el-button",{attrs:{type:"primary",size:"mini"},on:{click:e.updateRoleData}},[e._v("提交")])],1)],1)],1)},r=[],o=(a("7f7f"),a("5530")),s=a("365c"),l=a("2f62"),n=a("333d"),c={components:{Pagination:n["a"]},computed:Object(o["a"])({},Object(l["b"])(["sidebar","avatar","name","perms"])),data:function(){var e=this,t=function(e,t,a){var i=/^[a-zA-Z]{1}([a-zA-Z0-9]|[._]){4,19}$/;i.test(t)?a():a(new Error("只能输入5-20个以字母开头、可带数字、“_”、“.”的字串"))},a=function(t,a,i){a!=e.pwdForm.password?i(new Error("两次输入密码不一致!")):i()};return{listQuery:{keyword:"",pageNum:1,pageSize:20},list:[],total:0,loading:!0,editForm:{email:void 0,icon:void 0,id:void 0,note:void 0,status:void 0,userType:void 0,username:void 0},editFormRules:{userName:[{required:!0,message:"用户名不能为空",trigger:"blur"}],status:[{required:!0,message:"启用状态不能为空",trigger:"blur"}],userType:[{required:!0,message:"用户类型能为空",trigger:"blur"}],email:[{type:"email",message:"请输入正确的邮箱",trigger:"blur"}]},dialogStatus:"",textMap:{update:"编辑",create:"新增"},dialogFormVisible:!1,options:[],srcList:[],roleForm:{roleId:"",userId:""},roleFormRules:{},dialogFRoleVisible:!1,roleList:[],pwdForm:{password:"",password2:"",status:"",username:"",id:""},pwdFormRules:{password:[{required:!0,message:"登录密码不能为空",trigger:"blur"},{validator:t,trigger:"blur"}],password2:[{required:!0,message:"二次确认密码不能为空",trigger:"blur"},{validator:a,trigger:"blur"}]},dialogPwdVisible:!1}},created:function(){this.getList()},methods:{getList:function(){var e=this;this.loading=!0,Object(s["b"])("admin/list",this.listQuery).then((function(t){e.loading=!1,e.list=t.data.list,e.total=t.data.total})).catch((function(t){e.list=[],e.total=0,e.loading=!1}))},handleAdd:function(){var e=this;this.dialogFormVisible=!0,this.dialogStatus="create",this.editForm={email:"",icon:"",id:"",note:"",password:"123",status:1,userType:"",username:"",nickName:""},this.$nextTick((function(){e.$refs["editForm"].clearValidate()}))},allocationEdit:function(e,t){var a=this;this.dialogFRoleVisible=!0,this.roleForm={roleId:"",userId:t.id},Object(s["b"])("role/listAll").then((function(e){a.roleList=e.data})).catch((function(e){a.roleList=[]}))},createRoleData:function(){var e=this;this.$refs["roleForm"].validate((function(t){t&&Object(s["c"])("admin/updaterole",e.roleForm).then((function(t){200==t.code?(e.getList(),e.dialogFRoleVisible=!0,e.$message.success({message:"提交成功",type:"success"})):e.$message.error(t.message)})).catch((function(t){e.$message.error(t.message)}))}))},pwddit:function(e,t){var a=this;this.dialogPwdVisible=!0,this.pwdForm={password:"",password2:"",status:t.status,username:t.username,id:t.id},this.$nextTick((function(){a.$refs["pwdForm"].clearValidate()}))},updateRoleData:function(){var e=this;this.$refs["pwdForm"].validate((function(t){t&&Object(s["c"])("admin/resetpass",e.pwdForm).then((function(t){200==t.code?(e.dialogPwdVisible=!1,e.$message.success({message:"重置密码成功",type:"success"})):e.$message.error(t.message)})).catch((function(t){e.$message.error(t.message)}))}))},handleEdit:function(e,t){var a=this;if(t)for(var i in t)null!=t[i]&&"undefined"!=typeof t[i]||delete t[i];this.dialogStatus="update",this.dialogFormVisible=!0,this.editForm={email:t.email,icon:t.icon,id:t.id,note:t.note,password:t.password,status:t.status,userType:t.userType,username:t.username,nickName:t.nickName},this.$nextTick((function(){a.$refs["editForm"].clearValidate()}))},createData:function(){var e=this;this.$refs.editForm.validate((function(t){t&&Object(s["c"])("admin/".concat("create"==e.dialogStatus?"insert":"update"),e.editForm).then((function(t){200==t.code?(e.getList(),e.$message.success({message:"create"==e.dialogStatus?"添加成功":"修改成功",type:"success"}),e.dialogFormVisible=!1):e.$message.error(t.message)})).catch((function(t){e.$message.error(t.message)}))}))},handleDel:function(e,t){var a=this;Object(s["b"])({fun:"deluser",id:t.id}).then((function(i){a.$notify.success({title:"成功",message:"删除成功"}),a.list.splice(e,1),a.total--,a.$refs["popover-"+t.id].doClose()})).catch((function(e){a.$notify.error({title:"失败",message:e.message})}))},handleImagesUrlBefore:function(e){this.editForm.iconUrl=e.iconUrl;var t=e.name.substring(e.name.lastIndexOf(".")+1),a=e.size/1024/1024<2;return"png"!==t&&"jpg"!==t&&"jpeg"!==t&&"JPG"!==t&&"PNG"!==t?(this.$message({message:"只能上传图片（即后缀是.png或.jpg）的文件",type:"warning"}),!1):a?void 0:(this.$message.error("上传图片大小不能超过 2MB!"),!1)},doUpload:function(e){var t=this,a=new FormData;a.append("file",e.file),Object(s["a"])("upload/image",a).then((function(e){t.editForm.icon=e.data})).catch((function(e){t.$notify.error({title:"失败",message:e.message})}))},uploadUrl:function(e){this.editForm.icon=e.data.url},vbs:function(e){this.srcList=[],this.srcList.push(e)}}},u=c,d=(a("aa98"),a("2877")),p=Object(d["a"])(u,i,r,!1,null,"6a7aac84",null);t["default"]=p.exports},c5f6:function(e,t,a){"use strict";var i=a("7726"),r=a("69a8"),o=a("2d95"),s=a("5dbc"),l=a("6a99"),n=a("79e5"),c=a("9093").f,u=a("11e9").f,d=a("86cc").f,p=a("aa77").trim,m="Number",f=i[m],g=f,b=f.prototype,v=o(a("2aeb")(b))==m,h="trim"in String.prototype,w=function(e){var t=l(e,!1);if("string"==typeof t&&t.length>2){t=h?t.trim():p(t,3);var a,i,r,o=t.charCodeAt(0);if(43===o||45===o){if(a=t.charCodeAt(2),88===a||120===a)return NaN}else if(48===o){switch(t.charCodeAt(1)){case 66:case 98:i=2,r=49;break;case 79:case 111:i=8,r=55;break;default:return+t}for(var s,n=t.slice(2),c=0,u=n.length;c<u;c++)if(s=n.charCodeAt(c),s<48||s>r)return NaN;return parseInt(n,i)}}return+t};if(!f(" 0o1")||!f("0b1")||f("+0x1")){f=function(e){var t=arguments.length<1?0:e,a=this;return a instanceof f&&(v?n((function(){b.valueOf.call(a)})):o(a)!=m)?s(new g(w(t)),a,f):w(t)};for(var y,_=a("9e1e")?c(g):"MAX_VALUE,MIN_VALUE,NaN,NEGATIVE_INFINITY,POSITIVE_INFINITY,EPSILON,isFinite,isInteger,isNaN,isSafeInteger,MAX_SAFE_INTEGER,MIN_SAFE_INTEGER,parseFloat,parseInt,isInteger".split(","),F=0;_.length>F;F++)r(g,y=_[F])&&!r(f,y)&&d(f,y,u(g,y));f.prototype=b,b.constructor=f,a("2aba")(i,m,f)}},e498:function(e,t,a){"use strict";a("7456")},fdef:function(e,t){e.exports="\t\n\v\f\r   ᠎             　\u2028\u2029\ufeff"}}]);