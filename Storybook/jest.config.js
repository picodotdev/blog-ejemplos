module.exports = {
    transform: {
        '.(ts|tsx)': 'ts-jest',
	    '.(less)': 'jest-transform-stub'
    },
    moduleNameMapper: {
        ".(less)": "jest-transform-stub"
    },
    testRegex: './*\\.test\\.(ts|tsx)$',
    moduleFileExtensions: ['js', 'tsx', 'json']
};
