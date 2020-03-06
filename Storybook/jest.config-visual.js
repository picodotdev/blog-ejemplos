module.exports = {
    preset: 'jest-puppeteer',
    testRegex: './*\\.test-visual\\.ts$',
    setupFilesAfterEnv: ['./jest.setup.js']
};