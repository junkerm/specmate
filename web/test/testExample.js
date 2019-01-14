var assert = require("assert");
var webdriver = require("selenium-webdriver");

describe("testing javascript in the browser", function() {
  beforeEach(function() {
    if (process.env.SAUCE_USERNAME != undefined) {
      this.browser = new webdriver.Builder()
      .usingServer('http://'+'junkerm_qualicen'+':'+'nXKOEfYZQzKgtB7R6C+lYX+E13Lzjs8DWw2uj8SLtbz4Xg6XZuRq/nHAzHQvXPfmgm9gneXz5Ljr1HQ6cEa6U3cvHNrOyZEuEucVGCNfoqhJyCss7O136aJuDaK2quOu1HLda4Sj//uhZuXYtG/rhfy6YB6wUUvRwZiCgAt1hMUtuCgRJxAQXzLP8JBmjlmAT0amDNBk7MVTKp8xdpqe1/1tayhDppJCW88Iqob7GzzrN0hGRGiS4pzg7vsrVyVptKvZq9Ivj3Ipq8rncgSKE4pV4+ZpLcT+BKpVb42wRcfYH71fORlefs1Z9TVMiK+SYLpElgI7rOFY2Iub1kxHqM/Mg8HN1R36iSzzpXz0adCiaSkGoiraAvNfKFCnLpenRX7bsgr4iu39hKHJqjJN2Tgsy0msqDPi0ac8iJ36i2VbrUkAt6B3k96ollQTtqEczcRxhqwx0qA8xR6kl37xiUMZQa+Etdr9FwnjQfaehuohYsMJTgpe2BNBXIpWSaoEXPkLWjqHh3fKnwM6aEqo57WenOImYDb5LBJ+P/RmWz6dltnyL9J0hFKUSbilrg6w59O3p7uu3u3TSMcfJH6lY8bX8HUsTaGipVK5j2Z7q1J451ggMCNkMtbBjAkKkeUIMwvLzM5BQSEqV8x29MrEKK92LAibOCce7KntAVZv4B4='+'@ondemand.saucelabs.com:80/wd/hub')
      .withCapabilities({
        'tunnel-identifier': process.env.TRAVIS_JOB_NUMBER,
        build: process.env.TRAVIS_BUILD_NUMBER,
        username: 'junkerm_qualicen',
        accessKey: "nXKOEfYZQzKgtB7R6C+lYX+E13Lzjs8DWw2uj8SLtbz4Xg6XZuRq/nHAzHQvXPfmgm9gneXz5Ljr1HQ6cEa6U3cvHNrOyZEuEucVGCNfoqhJyCss7O136aJuDaK2quOu1HLda4Sj//uhZuXYtG/rhfy6YB6wUUvRwZiCgAt1hMUtuCgRJxAQXzLP8JBmjlmAT0amDNBk7MVTKp8xdpqe1/1tayhDppJCW88Iqob7GzzrN0hGRGiS4pzg7vsrVyVptKvZq9Ivj3Ipq8rncgSKE4pV4+ZpLcT+BKpVb42wRcfYH71fORlefs1Z9TVMiK+SYLpElgI7rOFY2Iub1kxHqM/Mg8HN1R36iSzzpXz0adCiaSkGoiraAvNfKFCnLpenRX7bsgr4iu39hKHJqjJN2Tgsy0msqDPi0ac8iJ36i2VbrUkAt6B3k96ollQTtqEczcRxhqwx0qA8xR6kl37xiUMZQa+Etdr9FwnjQfaehuohYsMJTgpe2BNBXIpWSaoEXPkLWjqHh3fKnwM6aEqo57WenOImYDb5LBJ+P/RmWz6dltnyL9J0hFKUSbilrg6w59O3p7uu3u3TSMcfJH6lY8bX8HUsTaGipVK5j2Z7q1J451ggMCNkMtbBjAkKkeUIMwvLzM5BQSEqV8x29MrEKK92LAibOCce7KntAVZv4B4=",
        browserName: "chrome", 
        doctor: true
      }).build();
    } else {
      this.browser = new webdriver.Builder()
      .withCapabilities({
        browserName: "chrome"
      }).build();
    }

    return this.browser.get("http://localhost:8080/-/login");
  });

  afterEach(function() {
    return this.browser.quit();
  });

  it("should handle clicking on a headline", function(done) {
    var headline = this.browser.findElement(webdriver.By.css('h1'));

    headline.click();

    headline.getText().then(function(txt) {
      assert.equal(txt, "awesome");
      done();
    });
  });
});
