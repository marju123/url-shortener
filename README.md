# Due to limited time some improvements should be added:
todo:
improve test coverage
improve restExceptionHandler
consider if add additional method getNotExpiredUrl() or will it be handled on FE side
consider if notActive record shouldBe removed or maintained (can it be overriden?)
add db model mapper
improve BeanValidation(extract separate service)
improve naming convention
add db tests
add integration tests
consider improvements for alias generation (optimizing db query, resolve/handle collision)


# API Endpoints
1. Create a short URL(alias):
        curl -X POST "http://localhost:8080/api/v1/url/create" \
        -d "url=https://example.com" \
        -d "customAlias=custom123" \
        -d "expirationDays=30"

        curl -X POST "http://localhost:8080/api/v1/url/create" \
        -d "url=https://example.com" \
        
        curl -X POST "http://localhost:8080/api/v1/url/create" \
        -d "url=https://example.com" \
        -d "expirationDays=30"
    
2. Redirect to original URL:
        curl -L "http://localhost:8080/api/v1/urls/custom123"
    
3. Delete alias:
        curl -X DELETE "http://localhost:8080/api/v1/urls/custom123"
