// 轮播
layui.use(['carousel', 'form'], function () {
    var carousel = layui.carousel
        , form = layui.form;
    carousel.render({
        elem: '#indexCarousel'
        , width: '1224px'
        , height: '488px'
        , interval: 3000
    });
});