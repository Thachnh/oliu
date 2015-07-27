Parse.initialize("6YcCS8AtuxwZYPbsKjeaOdCip4IR4dvd0RcArorN", "rt7PZiH5f6wVYSFgNNglO1UgivBG4nx1DKKaoHWP");
// Use Parse.Cloud.define to define as many cloud functions as you want.
// For example:
Parse.Cloud.define("hello", function(request, response) {
 Parse.Push.send({
  channels: [ "/event/IBdTb6nXTE" ],
  data: {
    alert: "The Giants won against the Mets 2-3."
  }
}, {
  success: function() {
    // Push was successful
	response.success("Hello world!");
  },
  error: function(error) {
    // Handle error
	response.error("I dun know!");
  }
});
});

