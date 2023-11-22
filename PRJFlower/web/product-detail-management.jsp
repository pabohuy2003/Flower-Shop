<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<html lang="en">

    <head>
        <meta charset="utf-8" />
        <meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no" />
        <link rel="apple-touch-icon" sizes="76x76" href="assets/img/apple-icon.png" />
        <link rel="icon" type="image/png" href="assets/img/favicon.png" />
        <title>Material Dashboard 2 by Creative Tim</title>
        <!--     Fonts and icons     -->
        <link rel="stylesheet" type="text/css"
              href="https://fonts.googleapis.com/css?family=Roboto:300,400,500,700,900|Roboto+Slab:400,700" />
        <!-- Nucleo Icons -->
        <link href="assets/css/nucleo-icons.css" rel="stylesheet" />
        <link href="assets/css/nucleo-svg.css" rel="stylesheet" />
        <!-- Font Awesome Icons -->
        <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.2.0/css/all.min.css"
              integrity="sha512-xh6O/CkQoPOWDdYTDqeRdPCVd1SpvCA9XXcUnZS2FmJNp1coAFzvtCN9BmamE+4aHK8yyUHUSCcJHgXloTyT2A=="
              crossorigin="anonymous" referrerpolicy="no-referrer" />
        <!-- Material Icons -->
        <link href="https://fonts.googleapis.com/icon?family=Material+Icons+Round" rel="stylesheet" />
        <!-- CSS Files -->
        <link id="pagestyle" href="assets/css/material-dashboard.css?v=3.0.5" rel="stylesheet" />
        <link rel="stylesheet" href="assets/css/product-detail-management.css">
    </head>

    <body class="g-sidenav-show bg-gray-200">
        <%@include file="component/sidebar-admin.jsp" %>
        <main class="main-content position-relative max-height-vh-100 h-100 border-radius-lg">
            <!-- Navbar -->
            <!-- End Navbar -->
            <div class="container-fluid py-3">
                <div class="row">
                    <div class="col-12">
                        <div class="card my-4">
                            <div class="card-header p-0 position-relative mt-n4 mx-3 z-index-2">
                                <div class="bg-gradient-primary shadow-primary border-radius-lg pt-4 pb-3 d-flex">
                                    <h6 class="text-white text-capitalize ps-3 m-0">
                                        <a style="color: white;" href="product-management.html">Product Management</a> / Product Detail
                                    </h6>
                                </div>
                            </div>
                            <div class="card-body p-0 pb-3">
                                <div class="row px-3">
                                    <div class="col-8 form-group pt-3">
                                        <form action="product-detail-management" method="post">
                                            <input type="hidden" name="productId" value="${requestScope.product.productId}">
                                            <div class="row form-group">
                                                <div class="col-6">
                                                    <fieldset>
                                                        <legend>Product Name</legend>
                                                        <input name="productName" type="text" class="form-control" value="${requestScope.product.productName}">
                                                    </fieldset>
                                                </div>
                                                <div class="col-6 form-group mb-3">
                                                    <fieldset>
                                                        <legend>Category</legend>
                                                        <Select name="categoryId">
                                                            <c:forEach items="${requestScope.lstCategory}" var="c">
                                                                <option <c:if test="${c.categoryId eq requestScope.product.categoryId}">selected=""</c:if> value="${c.categoryId}">${c.categoryName}</option>
                                                            </c:forEach>
                                                        </Select>
                                                    </fieldset>
                                                </div>
                                                <div class="col-6">
                                                    <fieldset>
                                                        <legend>Price</legend>
                                                        <input name="productPrice" min="10000" type="number" class="form-control" value="${requestScope.product.productPrice}">
                                                    </fieldset>
                                                </div>
                                                <div class="col-6"></div>
                                                <div class="col-6">
                                                    <div class="d-flex mt-3">
                                                        <label class="switch">
                                                            <input name="productIsFeatured" <c:if test="${requestScope.product.productIsFeatured}">checked=""</c:if> type="checkbox">
                                                                <span class="slider round"></span>
                                                            </label>
                                                            <h5 class="ms-3" style="line-height: 34px;">Featured Product</h5>
                                                        </div>
                                                    </div>
                                                    <div class="col-6 mt-2 row">
                                                        <div class="d-flex">
                                                            <label class="switch">
                                                                <input name="productIsRecent" <c:if test="${requestScope.product.productIsRecent}">checked=""</c:if> type="checkbox">
                                                                <span class="slider round"></span>
                                                            </label>
                                                            <h5 class="ms-3" style="line-height: 34px;">Recent Product</h5>
                                                        </div>
                                                    </div>
                                                    <div class="col-12 row mt-2">
                                                    <c:forEach items="${requestScope.lstProductColor}" var="pc" varStatus="i">
                                                        <div class="col-3">
                                                            <input value="${pc.productColorId}" name="productColorId" <c:if test="${pc.active}">checked=""</c:if> type="checkbox" class="btn-check w-100" id="btn-check-${pc.productColorId}"
                                                                   autocomplete="off">
                                                                <label class="btn btn-outline-primary w-100" for="btn-check-${pc.productColorId}">${pc.color.colorValue}</label><br>
                                                        </div>
                                                    </c:forEach>
                                                </div>
                                                <div class="col-12">
                                                    <fieldset>
                                                        <legend>Description</legend>
                                                        <textarea name="productDescription" class="form-control" name="" id="productDescription" cols="30"
                                                                  rows="10">${requestScope.product.productDescription}</textarea>
                                                    </fieldset>
                                                </div>
                                                <div>
                                                    <button class="btn bg-gradient-primary mt-4 w-100" type="submit">Save</button>
                                                </div>
                                            </div>
                                        </form>
                                    </div>
                                    <div class="col-4">
                                        <h4 class="mt-4">IMAGINE</h4>
                                        <div>
                                            <img style="height: 280px; border-radius: 8px; object-fit: cover;" class="w-100"
                                                 src="${requestScope.product.productImg}" alt="">
                                            <form id="formUpdateProductImg" action="update-productImg" method="post" enctype="multipart/form-data">    
                                                <input name="productImg" class="w-100 pt-2" type="file" onchange="document.getElementById('formUpdateProductImg').submit();">              
                                                <input type="hidden" name="productId" value="${requestScope.product.productId}">
                                            </form>
                                        </div>
                                        <div>
                                            <h4 class="mt-4">IMAGINE DETAIL</h4>
                                        </div>
                                        <div class="row">
                                            <c:forEach items="${requestScope.lstProductImgDetail}" var="i">
                                                <div class="col-6 mt-2" style="position: relative;">
                                                    <img style="height: 140px; border-radius: 8px; object-fit: cover;"
                                                         class="w-100" src="${i.productImgDetailPath}"
                                                         alt="">
                                                    <div class="topright"><a href=""><i
                                                                class="fa-regular fa-circle-xmark"></i></a></div>
                                                </div>
                                            </c:forEach>
                                            <input class="w-100 pt-2" type="file" name="imageFiles" multiple /><br /><br />
                                        </div>
                                    </div>
                                </div>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
        </main>
        <!--   Core JS Files   -->
        <script src="assets/js/core/popper.min.js"></script>
        <script src="assets/js/core/bootstrap.min.js"></script>
        <script src="assets/js/plugins/perfect-scrollbar.min.js"></script>
        <script src="assets/js/plugins/smooth-scrollbar.min.js"></script>
        <script>
                                                    var win = navigator.platform.indexOf("Win") > -1;
                                                    if (win && document.querySelector("#sidenav-scrollbar")) {
                                                        var options = {
                                                            damping: "0.5"
                                                        };
                                                        Scrollbar.init(document.querySelector("#sidenav-scrollbar"), options);
                                                    }
        </script>
        <!-- Github buttons -->
        <script async defer src="https://buttons.github.io/buttons.js"></script>
        <!-- Control Center for Material Dashboard: parallax effects, scripts for the example pages etc -->
        <script src="assets/js/material-dashboard.min.js?v=3.0.5"></script>
    </body>

</html>