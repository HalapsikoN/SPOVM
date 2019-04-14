<!DOCTYPE html>
<html lang="en" dir="ltr">
  <head>
    <meta charset="utf-8">
    <title>GetFromServer</title>
    <link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.3.1/css/bootstrap.min.css">
    <link rel="shortcut icon" href="https://image.flaticon.com/icons/png/512/93/93155.png">
    <style>
    body {
    background:  url(https://images.wallpaperscraft.ru/image/kot_morda_vzglyad_chernyy_fon_66486_1920x1080.jpg); /* Цвет фона и путь к файлу */
    }
    </style>
  </head>
  <body>
    <h1 style="text-align:center" class="text-white">GET FROM SERVER</h1>
    <br>
    <h3 style="text-align:center" class="text-white">Enter the header to get the information you saved</h3>
    <br><br><br>
    <form action="http://localhost/server/get" method="post">
      <div class="form-group">
        <label for="exampleInputEmail" class="text-white">Herader</label>
        <input class="form-control"  placeholder="Enter the header" name="header">
      </div>
        <button type="submit" class="btn btn-primary">Submit</button>
        <a class="btn btn-warning" href="http://localhost/server" role="button">Back to menu</a>
      </form>
      <h4><span class="badge badge-pill badge-danger">Sorry but: ${exception}</span></h4>
  </body>
</html>
