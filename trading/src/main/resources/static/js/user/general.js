// 导航栏
    // 缩小页面导航
    $('.menu.toggle').click(function () {
        $('.m-item').toggleClass('m-mobile-hide');
    });
    // 下拉表单
    $('.ui.dropdown').dropdown({
        on: 'hover'
    });

// 回到顶部
    $('#toTop-button').click(function () {
        $(window).scrollTo(0, 500);
    });

    var waypoint = new Waypoint({
        element: document.getElementById('waypoint'),
        handler: function (direction) {
            if (direction == 'down') {
                $('#toolbar').show(100);
            } else {
                $('#toolbar').hide(500);
            }
            console.log('Scrolled to waypoint! ' + direction);
        }
    })
