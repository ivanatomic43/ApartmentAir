
    mobiscroll.settings = {
        lang: 'en',                          // Specify language like: lang: 'pl' or omit setting to use default
        theme: 'ios',                        // Specify theme like: theme: 'ios' or omit setting to use default
            themeVariant: 'light'            // More info about themeVariant: https://docs.mobiscroll.com/4-10-6/calendar#opt-themeVariant
    };
    
    $(function () {
    
        // Mobiscroll Calendar initialization
        $('#demo-multi-day').mobiscroll().calendar({
            display: 'inline',               // Specify display mode like: display: 'bottom' or omit setting to use default
            select: 'multiple'               // More info about select: https://docs.mobiscroll.com/4-10-6/calendar#opt-select
        });
    
    
    });