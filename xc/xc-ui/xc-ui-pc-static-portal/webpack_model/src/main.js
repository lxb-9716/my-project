/*引入model02.js文件*/
var {add} = require('./model02')
/*引入vue.js的类库*/
var Vue = require('./vue.min')

/*MVVM模式中的model和VM部分*/
var VM = new Vue({
    el: '#app',
    data: {
        name: '张三',
        num1: 0,
        num2: 0,
        result: 0,
        list: [1, 2, 3, 4],
        user: {username: '张三', password: 123}
    },
    methods: {
        aaa: function () {
            /*在VM中引用数据对象中数据的时候需要添加this*/
            this.result = add(Number.parseInt(this.num1), Number.parseInt(this.num2));
        }
    }
});
