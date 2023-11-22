<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
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
        <link rel="stylesheet" href="assets/css/pagination.css" />
        <link rel="stylesheet" href="assets/css/product-detail-management.css" />

        <!-- Nepcha Analytics (nepcha.com) -->
        <!-- Nepcha is a easy-to-use web analytics. No cookies and fully compliant with GDPR, CCPA and PECR. -->
        <script defer data-site="YOUR_DOMAIN_HERE" src="https://api.nepcha.com/js/nepcha-analytics.js"></script>
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
                                        Product Management / <a style="color: white;" href="javascript:void(0)" data-bs-toggle="modal"
                                                                data-bs-target="#modal-add-product">Add More</a>
                                    </h6>
                                </div>
                            </div>
                            <div class="card-body px-0 pb-2">
                                <div class="table-responsive p-0">
                                    <table class="table align-items-center mb-0">
                                        <thead>
                                            <tr>
                                                <th class="text-uppercase text-secondary text-xxs font-weight-bolder opacity-7">
                                                    Product
                                                </th>
                                                <th class="text-center text-uppercase text-secondary text-xxs font-weight-bolder opacity-7">
                                                    Category Id
                                                </th>
                                                <th class="text-center text-uppercase text-secondary text-xxs font-weight-bolder opacity-7">
                                                    Price
                                                </th>
                                                <th class="text-center text-uppercase text-secondary text-xxs font-weight-bolder opacity-7">
                                                    View
                                                </th>
                                            </tr>
                                        </thead>
                                        <tbody>
                                            <c:forEach items="${requestScope.lstProduct}" var="p">
                                                <tr>
                                                    <td>
                                                        <div class="d-flex px-2 py-1">
                                                            <div>
                                                                <img src="${p.productImg}" class="avatar avatar-sm me-3 border-radius-lg"
                                                                     alt="user1" />
                                                            </div>
                                                            <div class="d-flex flex-column justify-content-center">
                                                                <h6 class="mb-0 text-sm">${p.productName}</h6>
                                                            </div>
                                                        </div>
                                                    </td>
                                                    <td class="align-middle text-center text-sm">
                                                        <span class="text-secondary text-xs font-weight-bold">${p.categoryId}</span>
                                                    </td>
                                                    <td class="align-middle text-center text-sm">
                                                        <span class="text-secondary text-xs font-weight-bold">${p.productPrice} VND</span>
                                                    </td>
                                                    <td class="align-middle text-center">
                                                        <a href="product-detail-management?productId=${p.productId}"><i
                                                                class="fa-solid fa-eye"></i></a>
                                                    </td>
                                                </tr>
                                            </c:forEach>
                                        </tbody>
                                    </table>
                                </div>
                            </div>
                        </div>
                    </div>
                </div>
                <div class="pagination pagination-style-three m-t-20">
                    <a <c:if test="${requestScope.pageCur eq 1}"> style="pointer-events: none; color: gray" </c:if> href="product-management?page=${requestScope.pageCur - 1}">Prev</a>
                    <c:forEach items="${requestScope.lstPage}" var="p">
                        <a href="product-management?page=${p}" <c:if test="${requestScope.pageCur eq p}"> class="selected" </c:if>>${p}</a>
                    </c:forEach>
                    <a <c:if test="${requestScope.pageCur eq requestScope.totalPage}"> style="pointer-events: none; color: gray" </c:if> href="product-management?page=${requestScope.pageCur + 1}">Next</a>
                    </div>
                </div>
            </div>
        </main>
    <%@include file="modal/modal-add-product.jsp"%>
    <!--   Core JS Files   -->
    <script src="assets/js/core/popper.min.js"></script>
    <script src="assets/js/core/bootstrap.min.js"></script>
    <script src="assets/js/plugins/perfect-scrollbar.min.js"></script>
    <script src="assets/js/plugins/smooth-scrollbar.min.js"></script>
    <script>
        var win = navigator.platform.indexOf("Win") > -1;
        if (win && document.querySelector("#sidenav-scrollbar")) {
            var options = {
                damping: "0.5",
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