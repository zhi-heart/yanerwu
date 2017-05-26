<script type="text/javascript">
    $(document).ready(function () {
        //slides the element with class "menu_body" when paragraph with class "menu_head" is clicked
        $("#firstpane .menu_head").click(function () {
            var thisChid = $(this).next().children(".menu_body");
            if (thisChid.is(":hidden")) {
                $(this).css({backgroundImage: "url(${basePath!}/images/admin/list_arrow_d.png)"});
            } else {
                $(this).css({backgroundImage: "url(${basePath!}/images/admin/list_arrow_r.png)"})
            }
            $(this).next().children(".menu_body").slideToggle(300);
            $(this).siblings(".menu_head").css({backgroundImage: "url(${basePath!}/images/admin/list_arrow_r.png)"}).next().children(".menu_body").hide(300);
        });

        $('.menu_body>a').each(function () {
            //alert(window.location.href + '\n' + $(this).attr('href') + "\n"+window.location.href.indexOf($(this).attr('href')));
            if (window.location.href.indexOf($(this).attr('href').split('?')[0]) > 0) {
                $(this).parent(".menu_body").slideToggle(0).siblings(".menu_body").slideUp("slow");
                $(this).parent().parent().prev().css({backgroundImage: "url(${basePath!}/images/admin/list_arrow_d.png)"});
                $(this).attr("class", "seld");
            } else {
                $(this).attr("class", "");
            }
        });

    });
</script>

<#macro buildMenu data>
    <#if data?? && data?size gt 0>
        <#list data as t>
            <#if  t.appAuthType?? && t.appAuthType=="1">
            <div class="menu_head">${t.appAuthName!}</div>
            <div>
                <@buildMenuChild data=t.children!/>
            </div>
            </#if>
        </#list>
    </#if>
</#macro>
<#macro buildMenuChild data>
    <#if data?? && data?size gt 0>
    <ul class="menu_body">
        <#list data as t>
            <#if t.appAuthType?? && t.appAuthType=="1">
                <a href="${basePath!}/admin/${t.appAuthAction!}">${t.appAuthName!}</a>
                <@buildMenuChild data=t.children! />
            </#if>
        </#list>
    </ul>
    </#if>
</#macro>

<div class="pl_box">
    <div id="firstpane" class="menu_list">
    <@buildMenu data=authoritiesTree!/>
        <div class="menu_head2"><a href="${basePath!}/admin/edit-password"
                                   style="font-size: 16px; color: #01568D;font-family: 微软雅黑;">修改密码</a></div>
        <div class="menu_head2"><a href="${basePath!}/syslogout" style="font-size: 16px; color: #01568D;font-family:微软雅黑 ;">安全退出</a>
        </div>
    </div>
</div>