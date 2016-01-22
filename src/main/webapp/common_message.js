$(document).ready(function() {
    $("#keyPairAlias").change(function() {
        var keyPairAlias = $("#keyPairAlias").val();
        var orgNumber = keyPairAlias.substring(0, 9);
        $("#senderOrgNumber").val(orgNumber);
    });
});