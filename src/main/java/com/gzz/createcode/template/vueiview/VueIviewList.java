package com.gzz.createcode.template.vueiview;

import com.gzz.createcode.common.Utils;
import com.gzz.createcode.mvc.model.Field;
import java.util.List;

public class VueIviewList {
	public static StringBuilder create(List<Field> fList, String clsUpp, String cName, String auth, String lowUpp) {
		StringBuilder sb = new StringBuilder();
		String idName = ((Field) fList.get(0)).getName();
		StringBuilder initform = new StringBuilder();
		StringBuilder cond = new StringBuilder();
		StringBuilder pageColum = new StringBuilder();
		for (Field field : fList) {
			String name = field.getName();
			String comments = field.getComment();
			cond.append("\r\n      <FormItem label=\"" + comments + "\"><Input placeholder=\"请输入" + comments
					+ "\" v-model=\"form." + name + "\"></Input></FormItem>");
			pageColum.append("\r\n          {title: '" + comments + "', key: '" + name + "'},");
		}
		sb.append(Utils.pageNote(cName + "列表", auth));
		sb.append("\r\n<template>");
		sb.append("\r\n  <div>");
		sb.append("\r\n  \t<h3>" + cName + "管理</h3>");
		sb.append("\r\n    <hr/>");
		sb.append("\r\n    <Form inline :label-width=\"70\">");
		sb.append(cond);

		sb.append("\r\n      <FormItem>");
		sb.append(
				"\r\n        <Button icon=\"search\" @click=\"refresh\" title=\"根据输入的条件查询\" type=\"primary\">查询</Button>");
		sb.append("\r\n        <Button type=\"primary\" icon=\"plus\" @click=\"doAdd()\" title=\"添加\" >添加</Button>");
		sb.append("\r\n      </FormItem>");
		sb.append("\r\n    </Form>");
		sb.append(
				"\r\n    <Table :loading=\"loading\" :columns=\"tableHeader\" :data=\"dataList\" style=\"width: 100%\"></Table>");

		sb.append("\r\n    <br/>");
		sb.append("\r\n    <div style=\"text-align: right\" v-if=\"total > 0\">");
		sb.append("\r\n      <Page size=\"small\" :current=\"page\" :total=\"total\" show-total");
		sb.append("\r\n            @on-change=\"(curr) => {this.page = curr ; this.refresh();}\"></Page>");
		sb.append("\r\n    </div>");
		sb.append("\r\n    <" + clsUpp + "Dialog ref=\"dialog\" :refresh=\"refresh\"></" + clsUpp + "Dialog>");
		sb.append("\r\n  </div>");
		sb.append("\r\n</template>");
		sb.append("\r\n<script>");
		sb.append("\r\n\timport {Message, Modal} from 'iview';");
		sb.append("\r\n\timport " + clsUpp + "Dialog from './" + clsUpp + "Dialog.vue';");
		sb.append("\r\n\timport " + clsUpp + "Expand from './" + clsUpp + "Expand.vue';");

		sb.append("\r\n");
		sb.append("\r\n  export default {");
		sb.append("\r\n    data: function () {");
		sb.append("\r\n      const that = this;");
		sb.append("\r\n      return {");
		sb.append("\r\n        tableHeader: [");
		sb.append("\r\n          {");
		sb.append("\r\n            type: 'expand',");
		sb.append("\r\n            width: 50,");
		sb.append("\r\n            render: (h, params) => {");
		sb.append("\r\n              return h(" + clsUpp + "Expand, {");
		sb.append("\r\n                props: {row: params.row}");
		sb.append("\r\n              })");
		sb.append("\r\n            }");
		sb.append("\r\n          },");
		sb.append(pageColum);
		sb.append("\r\n          {");
		sb.append("\r\n            title: '操作',");
		sb.append("\r\n            width: 150,");
		sb.append("\r\n            render: (h, params) => {");
		sb.append("\r\n              return h('div', [");
		sb.append("\r\n                h('Button', {");
		sb.append("\r\n                  props: {type:'primary',size:'small'},");
		sb.append("\r\n                  style: {marginRight: '5px'},");
		sb.append("\r\n                  on: {");
		sb.append("\r\n                    click: () => {");
		sb.append("\r\n                      that.doEdit(params.row)");
		sb.append("\r\n                    }");
		sb.append("\r\n                  }");
		sb.append("\r\n                }, '编辑'),");
		sb.append("\r\n                h('Button', {");
		sb.append("\r\n                  props: {type: 'primary',size: 'small'},");
		sb.append("\r\n                  on: {");
		sb.append("\r\n                    click: () => {");
		sb.append("\r\n                      that.doDelete(params.row)");
		sb.append("\r\n                    }");
		sb.append("\r\n                  }");
		sb.append("\r\n                }, '删除')");
		sb.append("\r\n              ]);");
		sb.append("\r\n            }");
		sb.append("\r\n          }");
		sb.append("\r\n        ],");
		sb.append("\r\n        total: 0,");
		sb.append("\r\n        page: 1,");
		sb.append("\r\n        dataList: [],");
		sb.append("\r\n        form: {");
		sb.append(initform);
		sb.append("\r\n        },");
		sb.append("\r\n        loading: false");
		sb.append("\r\n      }");
		sb.append("\r\n    },");
		sb.append("\r\n    computed: {},");
		sb.append("\r\n    created: function () {");
		sb.append("\r\n      this.refresh();");
		sb.append("\r\n    },");
		sb.append("\r\n    methods: {");
		sb.append("\r\n      refresh() {");
		sb.append("\r\n        const that = this;");
		sb.append("\r\n        that.loading = true;");
		sb.append("\r\n        const requestData = {...that.form, page: that.page - 1};");
		sb.append("\r\n        that.$http.post(\"/api/" + lowUpp
				+ "/queryPage\", JSON.stringify(requestData)).then(res => {");
		sb.append("\r\n          that.loading = false;");
		sb.append("\r\n          that.dataList = res.data.content;");
		sb.append("\r\n          that.total = res.data.totalElements;");
		sb.append("\r\n        }).catch(res => {");
		sb.append("\r\n        \t\tMessage.error({");
		sb.append("\r\n          \t\tcontent: \"获取" + cName + "列表失败：\" + res");
		sb.append("\r\n       \t\t });");
		sb.append("\r\n        });");
		sb.append("\r\n      },");
		sb.append("\r\n      doAdd() {");
		sb.append("\r\n        this.$refs[\"dialog\"].addDialog();");
		sb.append("\r\n      },");
		sb.append("\r\n      doEdit(row) {");
		sb.append("\r\n        this.$refs[\"dialog\"].editDialog(row);");
		sb.append("\r\n      },");
		sb.append("\r\n      doDelete(row) {");
		sb.append("\r\n        const that = this;");
		sb.append("\r\n     \t Modal.confirm({");
		sb.append("\r\n        \ttitle: '提示',");
		sb.append("\r\n       \t content: '您确定要删除吗?',");
		sb.append("\r\n      \t  closable:true,");
		sb.append("\r\n      \t  onOk: () => {");
		sb.append("\r\n       \t   this.$http.delete(\"/api/" + lowUpp + "/delete\", {");
		sb.append("\r\n            params: {ids: [row." + idName + "]}");
		sb.append("\r\n       \t   }).then(res => {");
		sb.append("\r\n         \t   Message.success({");
		sb.append("\r\n           \t   content: \"删除成功\"");
		sb.append("\r\n          \t  });");
		sb.append("\r\n          \t  that.refresh();");
		sb.append("\r\n          }).catch(res => {");
		sb.append("\r\n            Message.error({");
		sb.append("\r\n              content: \"删除失败：\" + res");
		sb.append("\r\n            });");
		sb.append("\r\n          });");
		sb.append("\r\n        },");
		sb.append("\r\n        onCancel: () => {");
		sb.append("\r\n        }");
		sb.append("\r\n      });");

		sb.append("\r\n      }");
		sb.append("\r\n    },");
		sb.append("\r\n    components: {" + clsUpp + "Dialog," + clsUpp + "Expand },");
		sb.append("\r\n  }");
		sb.append("\r\n</script>");
		sb.append("\r\n<style></style>");
		return sb;
	}
}
