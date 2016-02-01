$(document).ready(function() {
    $("#keyPairAlias").change(function() {
        var keyPairAlias = $("#keyPairAlias").val();
        var orgNumber = keyPairAlias.substring(0, 9);
        $("#senderOrgNumber").val(orgNumber);
    });
    $("#document").change(function() {
        refreshDocumentDialog();
    });
    refreshDocumentDialog();
});

var refreshDocumentDialog = function() {
    var mimes = $("#document")[0].files;
    var domPanel = $("#mimeElement");
    if (mimes.length == 0) {
        domPanel.hide();
        return;
    }
    domPanel.show();
    var domPanelBody = $("#mimeElement .panel-body");
    domPanelBody.html("");
    for (var i = 0; i < mimes.length; i++) {
        var mime = mimes[i];
        var domFormHiddenInputId = "hiddenInput";
        var domFormTextInputId = "mimeType";
        var domFormTextMimetypeInputId = "documentMimetype" + i;
        var domFormInputDiv = $("<div>");
        domFormInputDiv.attr("class", "col-sm-10");
        domFormInputDiv.append(createHiddenInput(domFormHiddenInputId, mime));
        domFormInputDiv.append(createTextInput(domFormTextMimetypeInputId, mime.type, "Mimetype"));
        var domFormGroup = $("<div>");
        domFormGroup.attr("class", "form-group");
        domFormGroup.append(createLabel(domFormTextInputId, "Nettleser sitt forslag: "));
        domFormGroup.append(domFormInputDiv);
        domPanelBody.append(domFormGroup);
    }

    function createLabel(domFormTextInputId, name) {
        var domFormLabel = $("<label>");
        domFormLabel.attr("class", "col-sm-2 control-label");
        domFormLabel.attr("for", domFormTextInputId);
        domFormLabel.text(name);
        return domFormLabel;
    }

    function createHiddenInput(domFormHiddenInputId, mime) {
        var domFormHiddenInput = $("<input>");
        domFormHiddenInput.attr("id", domFormHiddenInputId);
        domFormHiddenInput.attr("name", domFormHiddenInputId);
        domFormHiddenInput.attr("type", "hidden");
        domFormHiddenInput.val(mime.name);
        return domFormHiddenInput;
    }
    function createTextInput(domFormTextInputId, value, title) {
        var domFormTextInput = $("<input>");
        domFormTextInput.attr("id", domFormTextInputId);
        domFormTextInput.attr("name", domFormTextInputId);
        domFormTextInput.attr("class", "form-control");
        domFormTextInput.attr("type", "text");
        domFormTextInput.attr("autocomplete", "off");
        domFormTextInput.attr("title", title);
        if(value !== '') {
            domFormTextInput.attr("placeholder", value);
        }else{
            domFormTextInput.attr("placeholder", title+" ukjent");

        }
        domFormTextInput.val(value);
        return domFormTextInput;
    }
};