//上传图片
layui.use('upload', function () {
    var $ = layui.jquery
        , upload = layui.upload;
    var imgArray = new Array();//定义一个数组对象，用于存放上传后的图片地址，用户可能不是一次性上传所有图片

    //点击上传的时候，页面弹出一个提示框，避免用户重复点击
    $("#testListAction").click(function () {
        //配置一个透明的提示框
        layer.msg('正在上传中,请耐心等待,不要重复上传', {
            time: 3000, //3s后自动关闭
            offset: [100]
        });
    });
    //多文件列表示例，这一块是layui的上传，官网上都有
    var demoListView = $('#demoList')
        , uploadListIns = upload.render({
        elem: '#testList'
        , url: '/uploadImages'//我的上传接口
        , accept: 'images'//这里可以设置上传的类型限制
        , multiple: true
        , number: 5
        , auto: false
        , bindAction: '#testListAction'
        , choose: function (obj) {
            var files = this.files = obj.pushFile(); //将每次选择的文件追加到文件队列
            //读取本地文件
            obj.preview(function (index, file, result) {
                var tr = $(['<tr id="upload-' + index + '">'
                    , '<td>' + file.name + '</td>'
                    , '<td>' + (file.size / 1014).toFixed(1) + 'kb</td>'
                    , '<td>等待上传</td>'
                    , '<td>'
                    , '<button class="layui-btn layui-btn-xs layui-btn-danger demo-show">预览图片</button>'
                    , '<button class="layui-btn layui-btn-xs layui-btn-danger demo-delete">删除</button>'
                    , '</td>'
                    , '</tr>'].join(''));
                //删除
                tr.find('.demo-delete').on('click', function () {
                    delete files[index]; //删除对应的文件
                    tr.remove();
                    uploadListIns.config.elem.next()[0].value = ''; //清空 input file 值，以免删除后出现同名文件不可选
                });
                //预览图片，这里做了一个预览图片的功能，使用的是layui类型1的弹出层
                tr.find('.demo-show').on('click', function () {
                    layer.open({
                        type: 1,
                        title: false,
                        closeBtn: 1,
                        area: ['600px', '600px'],
                        content: '<img style="height: 600px;width: 600px" src="' + result + '" alt="' + file.name + '"  >'
                    });
                });
                demoListView.append(tr);
            });
        }
        //上传完成回调函数
        , done: function (res, index, upload) {
            var imgUrl = res.data;
            if (res.code == 0) { //上传成功
                imgArray.push(imgUrl);//图片上传成功后，将返回的图片路径放到全局imgArray
                var tr = demoListView.find('tr#upload-' + index)
                    , tds = tr.children();
                tds.eq(2).html('<span style="color: #5FB878;">上传成功</span>');
                tds.eq(3).html(''); //清空操作
                return delete this.files[index]; //删除文件队列已经上传成功的文件
            }
            this.error(index, upload);
        }
        , error: function (index, upload) {
            var tr = demoListView.find('tr#upload-' + index)
                , tds = tr.children();
        }
    });
});

//表单验证
$('.ui.form')
    .form({
        on: 'blur',
        fields: {
            name: {
                identifier: 'cName',
                rules: [
                    {
                        type: 'empty',
                        prompt: '商品名称不能为空'
                    },
                    {
                        type: 'maxLength[20]',
                        prompt: '商品名称最长二十位'
                    },
                ]
            },
            description: {
                identifier: 'description',
                rules: [
                    {
                        type: 'empty',
                        prompt: '商品详情不能为空'
                    }
                ]
            },
            price: {
                identifier: 'price',
                rules: [
                    {
                        type: 'regExp[/^([0-9][0-9]*)+(.[0-9]{1,2})?$/]',
                        prompt: '商品价格必须为大于等于0的数字,最多有两位小数'
                    }
                ]
            },
            startPrice: {
                identifier: 'startPrice',
                rules: [
                    {
                        type: 'regExp[/^([0-9][0-9]*)+(.[0-9]{1,2})?$/]',
                        prompt: '入手价格必须为大于等于0的数字,最多有两位小数'
                    }
                ]
            },
        }
    });


