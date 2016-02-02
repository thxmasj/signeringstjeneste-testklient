$(document).ready(function() {
    $("#keyPairAlias").change(function() {
        var keyPairAlias = $("#keyPairAlias").val();
        var orgNumber = keyPairAlias.substring(0, 9);
        $("#senderOrgNumber").val(orgNumber);
    });
    $("#mimeElement").hide();
    $("#document").change(function() {
        showMimetype();
    });
});

 function showMimetype() {
    var mimes = $("#document")[0].files[0];

    if (!mimes) {
        $("#mimeElement").hide();
        return;
    }
    $("#mimetype").val(mimes.type);
     $("#mimeElement").show();
}
