<title>代码生成器(gzz_gzz@163.com)</title>
<link rel="stylesheet" type="text/css" href="element.css">
<script type="text/javascript" src="vue.min.js"></script>
<script type="text/javascript" src="element.min.js"></script>
<script type="text/javascript" src="axios.min.js"></script>
<body>
<div id="app" style="font-size: 14px;">
    <div style="color: blue;">
        【<span style="color: red;">注意</span>】
        【调整[作者名]\[模块名],点击[查看字段]确保表名注释与字段名注释<span style="color: red;">为中文</span>,建议类名中表名前缀部分<span style="color: red;">已去掉</span>】
    </div>
    <div class="search">
        表名
        <el-input v-model="form.t_name" placeholder="请输入表名" style="width:100px" size="small"></el-input>
        <el-button @click="query" type="primary" size="small">查询</el-button>
        作者名
        <el-input v-model="form.auth" style="width:60px" size="small"></el-input>
        公司名
        <el-input v-model="form.company" style="width:60px" size="small"></el-input>
        项目名
        <el-input v-model="form.item_name" style="width:60px" size="small"></el-input>
        模块名
        <el-input v-model="form.model" style="width:60px" size="small"></el-input>
  swagger
  	<el-switch  v-model="form.swagger"  active-text="使用"  inactive-text="不使用" active-value="1" inactive-value="0"></el-switch>      
        <el-button @click="createCode" type="primary" size="small">生成代码</el-button>
 

    </div>
    <el-table :data="filterTableList" class="tabClass" @selection-change="onSelectChange" border size="small">
        <el-table-column type="selection" width="40px"></el-table-column>
        <el-table-column prop="t_name" label="表名"></el-table-column>
        <el-table-column prop="comment" label="注释"></el-table-column>
        <el-table-column label="建议类名">
            <template slot-scope="props">
                <el-input v-model="props.row.cls_upp" size="small"></el-input>
            </template>
        </el-table-column>
        <el-table-column label="类中文名">
            <template slot-scope="props">
                <el-input v-model="props.row.c_name" size="small"></el-input>
            </template>
        </el-table-column>
        <el-table-column label="操作" width="150px">
            <template slot-scope="props">
                <el-button @click="queryfieldList(props.row.t_name)" type="primary" size="small">查看字段</el-button>

            </template>
        </el-table-column>
    </el-table>
    <!--<br><br><div align="center">版权所有:高振中 联系方式:mail:gzz_gzz@163.com QQ:38967098</div>-->
    <el-dialog :visible.sync="show">
        <el-table :data="f_list" class="tabClass" border size="small">
            <el-table-column prop="name" label="字段名"></el-table-column>
            <el-table-column prop="comment" label="字段注释"></el-table-column>
            <el-table-column prop="type" label="JAVA数据类型"></el-table-column>
        </el-table>
    </el-dialog>

</div>
<script>
    axios.defaults.headers.post['Content-Type'] = 'application/json;charset=UTF-8';

    new Vue({
        el: '#app',
        data: function () {
            return {
                form: {
                    t_name: '',
                    c_list: [],
                    auth: "高振中",
                    company: "gzz",
                    model: "sys",
                    item_name: "study",
                    swagger: "0",
                },
                t_list: [],
                f_list: [],
                show: false,
                showSql: false,
            }
        },
        created: function () {
            this.query();
        },
        computed: {
            filterTableList() {
                const that = this;
                return that.t_list.filter(item => item.t_name.indexOf(that.form.t_name) != -1);
            }
        },
        methods: {
            queryfieldList(t_name) {
                this.show = true;
                axios.post("/code/queryField", JSON.stringify({"t_name_eq": t_name})).then(res => {
                    this.f_list = res.data;
                }).catch(res => {
                    this.$notify.error({title: '失败', message: '加载表信息败!'});
                });
            },
            openSql() {
                this.showSql = true;
            },
            onSelectChange(val) {
                this.form.c_list = val;
            },
            query() {
                axios.post("/code/queryList", JSON.stringify({"t_name": this.form.t_name})).then(res => {
                    this.t_list = res.data;
                }).catch(res => {
                    this.$notify.error({title: '失败', message: '加载表信息败!'});
                });
            },
            createCode() {
                if (this.form.c_list.length == 0) {
                    this.$notify({title: '警告', message: '请至少选择一条记录.!', type: 'warning'});
                    return;
                }
                axios.post("/code/create", JSON.stringify(this.form)).then(res => {
                    this.$notify.info({title: '消息', message: '生成代码成功'});
                    location.href = "/code/downCode";
                }).catch(res => {
                    this.$notify.error({title: '失败', message: '生成代码失败,请检查代码是否已经存在!'});
                });
            },
        },
    });
</script>
</body>
<style>
    #app {
        width: 1350px;
        margin: auto;
        position: absolute;
        top: 5px;
        left: 5px;
        right: 5px;
        bottom: 5px;
    }

    .search {
        padding-bottom: 10px;
        padding-top: 10px;
    }

    .tabClass .el-input--small .el-input__inner {
        height: 25px;
    }

    .tabClass .cell {
        font-size: 14px;
        line-height: 25px;
    }

    .el-table--small td, .el-table--small th {
        padding: 0px 0px;
    }

    .el-input__inner {
        padding: 0px 5px;
    }

    .el-button.el-button--primary.el-button--small {
        padding: 0px 5px;
        height: 22px;
    }

</style>