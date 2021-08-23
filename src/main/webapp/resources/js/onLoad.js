var d = document;

$(window).on('load', function () {
    var mainHeight = $('footer').offset().top - $('#main').offset().top;
    var mainHeightProperty = mainHeight + 'px';
    document.getElementById("main").style.height = mainHeightProperty;
});


