import puppeteer from 'puppeteer-extra';

it('visually looks correct', async () => {
    await page.goto('http://localhost:6006/iframe.html?selectedKind=components-helloworld&selectedStory=hello-world');

    const image = await page.screenshot();

    expect(image).toMatchImageSnapshot();
});
