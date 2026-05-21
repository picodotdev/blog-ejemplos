const gulp = require('gulp');
const babel = require('gulp-babel');
const eslint = require('gulp-eslint');
const uglify = require('gulp-uglify');
const sourcemaps = require('gulp-sourcemaps');

gulp.task('default', function() {
	gulp.src("src/main/js/**/*.js")
		.pipe(eslint())
		.pipe(eslint.format())
		.pipe(babel())
		.pipe(gulp.dest("build/dist/babel"))
		.pipe(sourcemaps.init({loadMaps: true}))
		.pipe(uglify())
		.pipe(gulp.dest("build/dist/uglify"))
		.pipe(sourcemaps.write('./'))
		.pipe(gulp.dest("build/dist"));
});
