
Description at /src/main/resources/README.md

sample calls:

http://localhost:8010/estimates?types=bugs,stories

  {
  
      "bugs": 10,
      "stories": 4
  
  }
  
http://localhost:8010/issuetypes/bugs

  {
  
      "name": "bug",
      "id": "/issuetypes/bug",
      "issues": 
  
      [
          "/issues/1",
          "/issues/2",
          "/issues/3"
      ]
  
  }

http://localhost:8010/issues/1
  
  {
  
      "issuetype": "/issuetypes/bug",
      "estimate": "3",
      "description": "Issue #1",
      "id": "/issues/1"
  
  }
