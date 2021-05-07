//表单验证
$('.ui.form')
    .form({
        on: 'blur',
        fields: {
            content: {
                identifier: 'content',
                rules: [
                    {
                        type: 'empty',
                        prompt: '求购内容不能为空'
                    }
                ]
            },
        }
    });


