# JWT
Json Web Token Login

Test:
Get request on localhost:8080 - 401 Unauthorized
Post request (Basic auth) on /api/auth/token with ["usuario"] and ["senha"]
Get request (Bearer Token) on localhost:8080 - 200 OK
