# generator-spring-gae-react [![NPM version][npm-image]][npm-url] [![Build Status][travis-image]][travis-url] [![Dependency Status][daviddm-image]][daviddm-url]
> Yeoman generator for a React app that runs atop Spring on Google App Engine

Version 2.x of this generator uses Spring Boot 2.x.

## Installation

First, install [Yeoman](http://yeoman.io) and generator-spring-gae-react using [npm](https://www.npmjs.com/) (we assume you have pre-installed [node.js](https://nodejs.org/)).

```bash
npm install -g yo
npm install -g generator-spring-gae-react
```

Then generate your new project:

```bash
yo spring-gae-react
```


## Development

It's always best to work on a generated project when updating. Each time you generate, do the following.

**In this project directory**
```
npm link
```

**Then run this each time to generate a project**
Note that this cleans your working directory, creates a new one, generates project and creates a local commit. The last part is important to help easily track your changes - you can compare your 
local filesystem to the last commit and then know what updates you did.

**Change the working dir** to suit your environment.
```
WORKING_DIR=~/dev/3wks; cd $WORKING_DIR && rm -rf $WORKING_DIR/tmp/test-spring && mkdir $WORKING_DIR/tmp/test-spring && cd $WORKING_DIR/tmp/test-spring && yo spring-gae-react && git init && git add . && git commit -m "initial commit"
```

## Publishing

```
npm version patch
npm publish
git push
```

## To Do

### Client
- [x] LESS
- [x] Autoprefixer
- [x] Minification
- [x] Hot Module Reloading
- [x] Add README doc to generated project
- [x] Generate favicon set
- [x] Split vendor and application bundles and use chunkhash
- [ ] Compress images
- [ ] Error handling + reporting

### Server
- [x] Sample API endpoint
- [ ] Sample frontend tests
- [x] Basic login and user management
- [x] Static ref data support

## Getting To Know Yeoman

 * Yeoman has a heart of gold.
 * Yeoman is a person with feelings and opinions, but is very easy to work with.
 * Yeoman can be too opinionated at times but is easily convinced not to be.
 * Feel free to [learn more about Yeoman](http://yeoman.io/).

## License

MIT © [3wks](https://www.3wks.com.au)


[npm-image]: https://badge.fury.io/js/generator-spring-gae-react.svg
[npm-url]: https://npmjs.org/package/generator-spring-gae-react
[travis-image]: https://travis-ci.org/3wks/generator-spring-gae-react.svg?branch=master
[travis-url]: https://travis-ci.org/3wks/generator-spring-gae-react
[daviddm-image]: https://david-dm.org/3wks/generator-spring-gae-react.svg?theme=shields.io
[daviddm-url]: https://david-dm.org/3wks/generator-spring-gae-react
